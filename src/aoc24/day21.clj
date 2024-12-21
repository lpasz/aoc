(ns aoc24.day21
  (:require [core :as c]))

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

(def rr (memoize (fn [n p]
                   (cond (c/one? n) (->> (find-paths p)
                                        (map (fn [p]
                                               (->> p
                                                    (map count)
                                                    (apply min))))
                                        (c/sum))
                         :else (->> (find-paths p)
                                    (map (fn [p]
                                           (->> p
                                                (map #(rr (dec n) %))
                                                (apply min))))
                                    (c/sum))))))

(defn find-best-path [number robots-in-the-way]
  (->>  (str "A" number)
        (partition 2 1)
        (map vec)
        (map (fn [p]
               (->> p
                    ;; num robot
                    (numpad-results)
                    (map #(rr robots-in-the-way %))
                    (apply min))))
        (c/sum)))

(defn complexities-of-robots [file n]
  (let [txt (slurp file)
        nums (c/extract-numbers txt)
        steps (re-seq #"\d+\w" txt)]
    (->> steps
         (map #(find-best-path % n))
         (map * nums)
         (c/sum))))

(defn part1 [file]
  (complexities-of-robots file 2))

(defn part2 [file]
  (complexities-of-robots file 25))


