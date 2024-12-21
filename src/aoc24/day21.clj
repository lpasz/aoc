(ns aoc24.day21
  (:require [core :as c]))

(defn part2 [file] :not-implemented)

(def numpad (c/to-matrix "789\n456\n123\n#0A"))

(def directional-keypad (c/to-matrix "#^A\n<v>" identity {\# \# \^ :up \v :down \< :<- \> :-> \A :a}))

(def dfs (memoize
          (fn [mtx queue goal? result mmin]
            (if (empty? queue) result
                (let [[idx curr moves visited? :as entry] (first queue)
                      queue (disj queue entry)]
                  (cond (< mmin idx) result
                        (goal? (mtx curr)) (recur mtx queue goal? (conj result moves) idx)
                        :else   (let [directions (c/directions curr)
                                      visited? (conj visited? curr)
                                      unvisited-directions (->> directions
                                                                (c/reject #(visited? (second %)))
                                                                (c/reject #(#{\#} (mtx (second %))))
                                                                (c/reject #(nil? (mtx (second %)))))]
                                  (recur mtx
                                         (reduce (fn [acc [dir coord]]
                                                   (conj acc [(inc idx) coord (conj moves dir) visited?]))
                                                 queue
                                                 unvisited-directions)
                                         goal?
                                         result
                                         mmin))))))))
(def numpad-results
  (let [nums "1234567890A"]
    (->> (for [a nums b nums]
           [[a b] (map #(concat % [:a])
                       (dfs numpad
                            (sorted-set [0
                                         (c/find-matrix-coord-of numpad a)
                                         []
                                         #{}])
                            #{b}
                            []
                            c/inf))])
         (into (sorted-map)))))

(def dirpad-results
  (let [dirs [:up :-> :<- :down :a]]
    (->> (for [a dirs b dirs]
           [[a b] (map #(concat % [:a])
                       (dfs directional-keypad
                            (sorted-set [0
                                         (c/find-matrix-coord-of directional-keypad a)
                                         []
                                         #{}])
                            #{b}
                            []
                            c/inf))])
         (into (sorted-map)))))

(defn find-paths [r1]
  (->> r1
       (concat [:a])
       (partition 2 1)
       (map vec)
       (map dirpad-results)))

(->> '((((:down :a)) ((:<- :up :a) (:up :<- :a)) ((:-> :a)))
       (((:<- :a)) ((:-> :down :a) (:down :-> :a)) ((:up :a))))
     (map #(c/sum (map count %))))

(->> (find-paths '(:<- :a))
     (map (fn [rs]
            (->> rs
                 (map find-paths)
                 (map #(c/sum (map count %)))
                 (apply min))))
     (c/sum))

(defn find-best-path [number]
  (->>  (str "A" number)
        (partition 2 1)
        (map vec)
        (map numpad-results)
        (map (fn [parts]
               (->> parts
                    (map (fn [part]
                           (->> (find-paths part)
                                (map (fn [rs]
                                       (->> rs
                                            (map find-paths)
                                            (map (fn [out-path]
                                                   (->> out-path
                                                        (map #(->> %
                                                                   (map count)
                                                                   (apply min)))
                                                        (c/sum))))
                                            (apply min))))
                                (c/sum))))
                    (apply min))))
        (c/sum)))

(find-best-path "029A")

(defn part1 [file]
  (let [txt (slurp file)
        nums (c/extract-numbers txt)
        steps (re-seq #"\d+\w" txt)]
    (->> steps
         (map find-best-path)
         (map * nums)
         (c/sum))))

(part1 "./assets/day21/input.txt")
(part1 "./assets/day21/example.txt")


