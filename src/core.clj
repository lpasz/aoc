(ns core
  (:require [clojure.pprint :as pp]
            [clojure.string :as str]))

;; create a `.clj-kondo/config.edn` and add the line below
;; {:lint-as {core/then clojure.core/fn}}
(defmacro then
  ([fun value] `(~fun ~value))
  ([args body value] `((fn ~args ~body) ~value)))

(defn fnvec [& funs]
  "create a function that receives a coll/vector and
  returns a vector with first fn applied to first elem, second to second

   example:
   ((fnvec inc dec core/parse-int) [0 3 \"3\"]) #=> [1 2 3]"
  (fn [args] (mapv (fn [fun args] (fun args)) funs args)))

(defn next-position
  "Given x y points, and it's velocities vx vy gives the next position.
   It will wrap if hit the borders of size-x and size-y"
  [[x y vx vy] [size-x size-y]]
  (let [[nx ny] [(+ x vx) (+ y vy)]
        nx (cond (< nx 0) (+ nx size-x)
                 (> nx (dec size-x)) (rem nx size-x)
                 :else nx)
        ny (cond (< ny 0) (+ ny size-y)
                 (> ny (dec size-y)) (rem ny size-y)
                 :else ny)]
    [nx ny]))

(defn create-matrix [x y value]
  (into (sorted-map)
        (for [x (range 0 x) y (range 0 y)]
          [[x y] value])))

(defn add-to-matrix
  ([mtx points value]
   (add-to-matrix mtx (map #(vector % value) points)))
  ([mtx points-and-values]
   (reduce (fn [acc [[x y] value]]
             (assoc acc [x y] value)) mtx points-and-values)))

(defn map-key [fun coll]
  (map (fn [[key value]] [(fun key) value]) coll))

(defn filter-by-key [fun coll]
  (filter (fn [[key _]] (fun key)) coll))

(defn filter-by-value [fun coll]
  (filter (fn [[_ value]] (fun value)) coll))

(defn max-by [fun coll]
  (apply max (map fun coll)))

(defn min-by [fun coll]
  (apply min (map fun coll)))

(defn flatten-once [coll]
  (mapcat identity coll))

(defn queue [coll]
  (reduce conj clojure.lang.PersistentQueue/EMPTY coll))

(defn parse-int
  "Either parse the string to int, or return nil"
  [int]
  (try (Integer/parseInt int)
       (catch Exception _ nil)))

(defn surrounding-xy [[x y]]
  [[(dec x) (inc y)] [x (inc y)] [(inc x) (inc y)]
   [(dec x) y]                   [(inc x) y]
   [(dec x) (dec y)] [x (dec y)] [(inc x) (dec y)]])

(defn up-down-left-right [[x y]]
  [[x (dec y)]
   [x (inc y)]
   [(dec x) y]
   [(inc x) y]])

(defn diagonals [[x y]]
  [[(inc x) (dec y)]
   [(inc x) (inc y)]
   [(dec x) (inc y)]
   [(dec x) (dec y)]])

(defn x [[x y] len]
  (let [rng (range 0 (inc len))]
    [(map (fn [n] [(+ x n) (+ y n)]) rng)
     (map (fn [n] [(- x n) (- y n)]) rng)
     (map (fn [n] [(+ x n) (- y n)]) rng)
     (map (fn [n] [(- x n) (+ y n)]) rng)]))

(defn cross [[x y] len]
  (let [rng (range 0 (inc len))]
    [(map (fn [n] [(+ x n) y]) rng)
     (map (fn [n] [(- x n) y]) rng)
     (map (fn [n] [x (+ y n)]) rng)
     (map (fn [n] [x (- y n)]) rng)]))

(defn cross-and-x [coord len]
  (concat (x coord len) (cross coord len)))

(defn movements [moves]
  (->> (re-seq #"[<>^v]" moves)
       (map #(case %
               \^ :up "^" :up
               \v :down "v" :down
               \< :<- "<" :<-
               \> :-> ">" :->))))

(defn directions [[x y]]
  {:up   [x (dec y)]
   :down [x (inc y)]
   :<-   [(dec x) y]
   :->   [(inc x) y]})

(defn insp
  ([v] (pp/pprint v) v)
  ([id v] (pp/pprint {id v}) v))

(defn find-matrix-coord-of [mtx value]
  (->> mtx
       (filter-by-value #(= % value))
       (ffirst)))

(defn to-matrix
  ([inp] (to-matrix inp identity identity))
  ([inp line-parse item-parse]
   (->> (if (string? inp)
          (str/split-lines inp)
          inp)
        (map-indexed (fn [idy line] (->> line line-parse (map-indexed (fn [idx c] [[idx idy] (item-parse c)])))))
        (flatten-once)
        (into (sorted-map)))))

(defn print-matrix [mtx]
  (println "")
  (->> mtx
       (group-by (comp second first))
       (into (sorted-map))
       (then [sor]
             (doseq [[_ lines] sor]
               (println (reduce str "" (map second lines))))))
  mtx)

(defn one? [n] (= 1 n))

(defn third [coll] (nth coll 2))

(defn transpose [matrix]
  (apply map vector matrix))

(defn remove-first [pred coll]
  (lazy-seq
   (when (seq coll)
     (let [[y & ys] coll]
       (if (pred y)
         ys
         (cons y (remove-first pred ys)))))))

(defn reject [pred coll]
  (filter #(not (pred %)) coll))

(defn rejectv [pred coll]
  (filterv #(not (pred %)) coll))

(defn sum [coll]
  (reduce + coll))

(defn product [coll]
  (reduce * coll))

(defn shoelaces-formula-area
  "We are using the polygon version.
   See more: https://en.wikipedia.org/wiki/Shoelace_formula"
  [polygon-points]
  (->> polygon-points
       (partition 2 1)
       (map (fn [[[x1 y1] [x2 y2]]] (* (+ y1 y2) (- x1 x2))))
       (sum)
       (then [n] (quot n 2))
       (abs)))

(defn pick-theorem-internal-points
  "Normaly used to find the area, since we found the area with shoelace, we are using it to get the number of internal points.
   See more: https://en.wikipedia.org/wiki/Pick%27s_theorem"
  [area polygon-points-cnt]
  (- area (- (quot polygon-points-cnt 2) 1)))

(defn revert-map
  ([m] (revert-map m []))
  ([m type]
   (reduce (fn [acc [k vs]]
             (reduce (fn [acc v]
                       (if (acc v)
                         (update acc v #(conj % k))
                         (assoc acc v (into type [k]))))
                     acc
                     vs))
           {}
           m)))

(defn partition-while [pred-prev-curr coll]
  (loop [coll coll
         previous nil
         c []
         result []]
    (if (empty? coll)
      result
      (cond (nil? previous) (recur (rest coll) (first coll) (conj c (first coll)) result)
            (pred-prev-curr previous (first coll)) (recur (rest coll) (first coll) (conj c (first coll)) result)
            :else (recur (rest coll) (first coll) [(first coll)] (conj result c))))))

(def neg-inf Double/NEGATIVE_INFINITY)

(defn update-costs-longest [g costs unvisited curr]
  (let [curr-cost (get costs curr)]
    (reduce-kv
     (fn [c nbr nbr-cost]
       (if (unvisited nbr)
         (update-in c [nbr] max (+ curr-cost nbr-cost))
         c))
     costs
     (get g curr))))

(defn dijkstra-longest
  ([g src]
   (dijkstra-longest g src nil))
  ([g src dst]
   (loop [costs (assoc (zipmap (keys g) (repeat neg-inf)) src 0)
          curr src
          unvisited (disj (apply hash-set (keys g)) src)]
     (cond
       (= curr dst) (select-keys costs [dst])

       (or (empty? unvisited) (= neg-inf (get costs curr))) costs

       :else (let [next-costs (update-costs-longest g costs unvisited curr)
                   next-node (apply max-key next-costs unvisited)]
               (recur next-costs next-node (disj unvisited next-node)))))))

(def inf Double/POSITIVE_INFINITY)

(defn update-costs-shortest
  [g costs unvisited curr]
  (let [curr-cost (get costs curr)]
    (reduce-kv
     (fn [c nbr nbr-cost]
       (if (unvisited nbr)
         (update-in c [nbr] min (+ curr-cost nbr-cost))
         c))
     costs
     (get g curr))))

(defn dijkstra-shortest
  ([g src]
   (dijkstra-shortest g src nil))
  ([g src dst]
   (loop [costs (assoc (zipmap (keys g) (repeat inf)) src 0)
          curr src
          unvisited (disj (apply hash-set (keys g)) src)]
     (cond
       (= curr dst)
       (select-keys costs [dst])

       (or (empty? unvisited) (= inf (get costs curr)))
       costs

       :else
       (let [next-costs (update-costs-shortest g costs unvisited curr)
             next-node (apply min-key next-costs unvisited)]
         (recur next-costs next-node (disj unvisited next-node)))))))

(defn rotations [coll]
  (let [cnt (count coll)
        rng (range 0 cnt)]
    (->> rng
         (map #(take cnt (drop % (cycle coll)))))))

(defn intersect [line1 line2]
  (let [div (- (:slope  line2) (:slope  line1))]
    (if (zero? div)
      nil
      (let [x (/ (- (:offset line1) (:offset line2)) div)
            y (+ (* (:slope line1) x)
                 (:offset line1))]
        [x y]))))

(defn compute-line
  [[x1 y1] [x2 y2]]
  (when-not (zero? (- x2 x1))
    (let [m (/ (- y2 y1)
               (- x2 x1))]
      {:slope  m
       :offset (- y1 (* m x1))})))

(defn compute-point-for-x [[x1 y1] slope x]
  [x (+ (* slope (- x x1)) y1)])

(defn unique-combinations [coll1 coll2]
  (->> (for [a coll1
             b coll2
             :when (not= a b)]
         #{a b})
       (set)
       (map #(into [] %))))

(defn- pivot-matrix
  "Make matrix diagonal only with 1"
  [mtx]
  (let [size (count mtx)]
    (->> (range 0 size)
         (reduce (fn [acc i]
                   (let [row (get acc i)
                         value (get row i)]
                     (assoc acc i (mapv #(/ % value) row))))
                 mtx))))

(defn gaussian-elimination
  "Make the gaussian elimination proccess"
  [mtx]
  (let [size (count mtx)]
    (->> (range 0 (dec size))
         (reduce (fn [acc i]
                   (->> (range (inc i) size)
                        (reduce (fn [acc j]
                                  (let [curr-row (get acc i)
                                        next-row (get acc j)
                                        curr-value  (get curr-row i)
                                        next-value (get next-row i)
                                        div (/ next-value curr-value)
                                        result (->> (interleave curr-row next-row)
                                                    (partition 2)
                                                    (mapv (fn [[currv nextv]]
                                                            (- nextv (* currv div)))))]

                                    (assoc acc j result)))
                                acc)))
                 mtx))))

(defn back-substitute-triangule
  "Back solve the triangule with the newly found values"
  [mtx]
  (let [size (count mtx)]
    (->> (range 1 size)
         (reverse)
         (reduce (fn [acc i]
                   (->> (range 0 i)
                        (reverse)
                        (reduce (fn [acc j]
                                  (let [curr-row (get acc i)
                                        prev-row (get acc j)
                                        curr-value  (get curr-row i)
                                        prev-value (get prev-row i)
                                        div (if (zero? curr-value)
                                              0
                                              (/ prev-value curr-value))
                                        result (->> (interleave curr-row prev-row)
                                                    (partition 2)
                                                    (mapv (fn [[currv prevv]]
                                                            (- prevv (* currv div)))))]
                                    (assoc acc j result)))
                                acc)))
                 mtx))))

(defn inverse-matrix [mtx]
  (->> (pivot-matrix (vec mtx))
       (gaussian-elimination)
       (pivot-matrix)
       (back-substitute-triangule)
       (transpose)
       (last)))


