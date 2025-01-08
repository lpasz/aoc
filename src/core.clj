(ns core
  (:require [clojure.pprint :as pp]
            [clojure.set :as set]
            [clojure.string :as str])
  (:import java.security.MessageDigest
           java.math.BigInteger))

(defn md5 [s]
  (let [algorithm (MessageDigest/getInstance "MD5")
        raw (.digest algorithm (.getBytes s))]
    (format "%032x" (BigInteger. 1 raw))))

(defmacro get-input
  ([] (get-input "input.txt"))
  ([file]
   `(str/trim (slurp (str "./assets/"
                (-> ~*ns*
                    (ns-name)
                    (str)
                    (str/replace "-test" "")
                    (str/replace "." "/"))
                "/"
                ~file)))))

(defmacro cond-fn [& clauses]
  (cond (empty? clauses) nil
        (not (even? (count clauses))) (throw (ex-info (str `cond-fn " requires an even number of forms")
                                                      {:form &form :meta (meta &form)}))
        :else (let [[expr fn-value & rest-clauses] clauses]
                `(if ~expr
                   (~fn-value ~expr)
                   (cond-let ~@rest-clauses)))))

(defmacro then
  ([fun value] `(~fun ~value))
  ([args body value] `((fn ~args ~body) ~value)))

(defn fnvec
  "create a function that receives a coll/vector and
  returns a vector with first fn applied to first elem, second to second

   example:
   ((fnvec inc dec core/parse-int) [0 3 \"3\"]) #=> [1 2 3]"
  [& funs]
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

(defn euclidean-distance [[x1 y1] [x2 y2]]
  (+ (abs (- x2 x1)) (abs (- y2 y1))))

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
  ([input] (to-matrix input identity identity))
  ([input line-parse item-parse]
   (->> (if (string? input)
          (str/split-lines input)
          input)
        (map-indexed (fn [idy line] (->> line line-parse (map-indexed (fn [idx c] [[idx idy] (item-parse c)])))))
        (flatten-once)
        (into (sorted-map)))))

(defn cartesian-product [parts]
  (if (= (count parts) 1)
    parts
    (let [[a b & tail] parts]
      (for [a a b b]
        [a b]))))

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

(defn hex->long
  "Converts number from hex to long"
  [n]
  (read-string (str "16r" n)))

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
       (= curr dst) costs

       (or (empty? unvisited) (= inf (get costs curr))) costs

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

(defn extract-numbers [text]
  (->> (re-seq #"-?\d+" text)
       (map parse-long)))

(defn matrix-to-graph
  ([mtx] (matrix-to-graph mtx #{\.}))
  ([mtx valid?]
   (->> mtx
        (filter #(valid? (second %)))
        (keys)
        (map (fn [coord] [coord (up-down-left-right coord)]))
        (map (fn [[coord ncoords]] [coord (filter #(valid? (mtx %)) ncoords)]))
        (map (fn [[coord ncoords]] [coord (map (fn [coord] [coord 1]) ncoords)]))
        (map (fn [[coord ncoords]] [coord (into (sorted-map) ncoords)]))
        (into (sorted-map)))))

(defn matrix-values-to-graph
  [mtx valid?]
  (->> mtx
       (filter #(valid? (second %)))
       (keys)
       (map (fn [coord] [(mtx coord) (filter valid? (map mtx (up-down-left-right coord)))]))
       (map (fn [[coord ncoords]] [coord (map (fn [coord] [coord 1]) ncoords)]))
       (map (fn [[coord ncoords]] [coord (into (sorted-map) ncoords)]))
       (into (sorted-map))))

;; (matrix-values-to-graph (to-matrix "789\n456\n123\n#0A") #{\0 \1 \2 \3\ \4 \5 \6 \7 \8 \9 \A})

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

(defn unseen? [path node]
  (not-any? #{node} path))

(defn step-factory [parent last-insertion cost heur dest]
  (fn [insertion-idx node]
    {:parent parent
     :node node
     :entered (+ last-insertion (inc insertion-idx))
     :cost (+ (:cost parent) (cost (:node parent) node) (heur node dest))}))

(defn rpath [{:keys [node parent]}]
  (lazy-seq
   (cons node (when parent (rpath parent)))))

(defn cmp-step [step-a step-b]
  (let [cmp (compare (:cost step-a) (:cost step-b))]
    (if (zero? cmp)
      (compare (:entered step-a) (:entered step-b))
      cmp)))

(defn next-a*-path [graph dest adjacent f-cost f-heur]
  (when-let [{:keys [node] :as current} (first adjacent)]
    (let [path (rpath current)
          adjacent' (disj adjacent current)] ;; "pop" the current node
      (if (= node dest)
        [(reverse path), adjacent']
        (let [last-idx (or (:entered (last adjacent')) 0)
              factory (step-factory current last-idx f-cost f-heur dest)
              xform (comp (filter (partial unseen? path)) (map-indexed factory))
              adjacent'' (into adjacent' xform (get graph node))]
          (recur graph dest adjacent'' f-cost f-heur))))))
(defn a*-seq
  "Construct a lazy sequence of calls to `next-a*-path`, returning the shortest path first."
  [graph dest adjacent distance heuristic]
  (lazy-seq
   (when-let [[path, adjacent'] (next-a*-path graph dest adjacent distance heuristic)]
     (cons path (a*-seq graph dest adjacent' distance heuristic)))))

(defn cost-to-graph [cost]
  (->> cost
       (map (fn [[k v]] [k (set (keys v))]))
       (into {})))

(defn a*
  "A sequence of paths from `src` to `dest`, shortest first, within the supplied `graph`.
  If the graph is weighted, supply a `distance` function. To make use of A*, supply a 
  heuristic function. Otherwise performs like Dijkstra's algorithm."
  [graph src dest & {:keys [distance heuristic]}]
  (let [init-adjacent (sorted-set-by cmp-step {:node src :cost 0 :entered 0})]
    (a*-seq graph dest init-adjacent
            (or distance (constantly 1))
            (or heuristic (constantly 0)))))

(defn a**
  "A sequence of paths from `src` to `dest`, shortest first, within the supplied `graph`.
  If the graph is weighted, supply a `distance` function. To make use of A*, supply a 
  heuristic function. Otherwise performs like Dijkstra's algorithm."
  [graph src dest & {:keys [distance heuristic]}]
  (let [cost graph
        graph (cost-to-graph cost)
        init-adjacent (sorted-set-by cmp-step {:node src :cost 0 :entered 0})]
    (a*-seq graph dest init-adjacent
            (or distance (constantly 1))
            (or heuristic (constantly 0)))))

(comment
  (def graph
    "Map of node => set of adjacent nodes."
    {"A" #{"B", "E"}
     "B" #{"A", "C", "D"}
     "C" #{"B", "D"}
     "D" #{"B", "C", "E"}
     "E" #{"A", "D"}})

  (def costs
    "Map of node => adjacent node => cost. This could
  be replaced with any cost function of the shape
  (node, node') => cost."
    {"A" {"B" 2, "E" 10}
     "B" {"A" 2, "C" 3, "D" 4}
     "C" {"B" 3, "D" 2}
     "D" {"B" 4, "C" 3, "E" 10}
     "E" {"A" 10, "D" 10}})

  (a* graph "A" "D")
  (a* graph "A" "D" costs)
;;
  )

(defn bron-kerbosch
  "Use to find all cliques of a graph/network"
  ([nodes neighbours] (bron-kerbosch #{} nodes #{} neighbours))
  ([r nodes x neighbours]
   (if (every? empty? [nodes x])
     [r]
     (loop [nodes nodes
            x x
            result []]
       (if (empty? nodes)
         result
         (let [node (first nodes)
               nodes (disj nodes node)
               result (into result
                            (bron-kerbosch (conj r node)
                                           (set/intersection nodes (neighbours node))
                                           (set/intersection x (neighbours node))
                                           neighbours))
               x (conj x node)]
           (recur nodes x result)))))))

(defn gcd
  "Greatest Common Denominator"
  [x y]
  (cond (= x 0) y
        (= y 0) x
        (= x y) x
        (> x y) (if (zero? (rem x y)) y  (recur (- x y) y))
        :else (if (zero? (rem y x)) x (recur y (- y x)))))

(defn lcm
  "Lowest Common Multiplier"
  [x y]
  (* (/ x (gcd x y)) y))

(defn visit-all-nodes-distances
  ([graph start-coord] (visit-all-nodes-distances graph start-coord #{} 0))
  ([graph start-coord visited distance]
   (let [visited (conj visited start-coord)
         nexts (->> (graph start-coord)
                    (reject (fn [[next-coord _]] (visited next-coord))))]
     (if (empty? nexts)
       [distance]
       (mapcat (fn [[next-coord dist]]
                 (visit-all-nodes-distances graph
                                            next-coord
                                            visited
                                            (+ distance dist))) nexts)))))

