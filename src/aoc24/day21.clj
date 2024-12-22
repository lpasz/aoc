(ns aoc24.day21
  (:require [core :as c]
            [clojure.string :as s]))

(def convert-numpad {\9 9 \8 8 \7 7 \6 6 \5 5 \4 4
                     \3 3 \2 2 \1 1 \0 0 \# \# \A :a})

(def numpad
  (-> (s/join "\n" ["789"
                    "456"
                    "123"
                    "#0A"])
      (c/to-matrix identity convert-numpad)))

(def directional-keypad
  (-> (s/join "\n" ["#^A"
                    "<v>"])
      (c/to-matrix identity {\# \# \^ :up \v :down \< :<- \> :-> \A :a})))

(def dfs
  (memoize
   (fn
     ([mtx start end] (dfs mtx
                           (sorted-set [0 (c/find-matrix-coord-of mtx start) [] #{}])
                           #{end}
                           []
                           c/inf))
     ([mtx queue goal? result mmin]
      (if (empty? queue)
        result
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
                                 mmin)))))))))
(def numpad-results
  (let [nums [1 2 3 4 5 6 7 8 9 0 :a]]
    (->> (for [a nums b nums]
           [[a b] (->> (dfs numpad a b)
                       (map #(concat % [:a])))])
         (into {}))))

(def dirpad-results
  (let [dirs [:up :-> :<- :down :a]]
    (->> (for [a dirs b dirs]
           [[a b] (->> (dfs directional-keypad a b)
                       (map #(concat % [:a])))])
         (into (sorted-map)))))

(defn find-paths [r1]
  (->> r1
       (concat [:a])
       (partition 2 1)
       (map vec)
       (map dirpad-results)))

(def simulate-dir-robot
  (memoize
   (fn [robot-number p]
     (if (c/one? robot-number)
       (->> (find-paths p)
            (map (fn [p]
                   (->> p
                        (map count)
                        (apply min))))
            (c/sum))
       (->> (find-paths p)
            (map (fn [p]
                   (->> p
                        (map #(simulate-dir-robot (dec robot-number) %))
                        (apply min))))
            (c/sum))))))

(defn find-best-path [number robots-in-the-way]
  (->>  (str "A" number)
        (map convert-numpad)
        (partition 2 1)
        (map vec)
        (map (fn [p]
               (->> p
                    (numpad-results) ;; num-pad-robot
                    (map #(simulate-dir-robot robots-in-the-way %)) ;; dir-pad-robots
                    (apply min))))
        (c/sum)))

(defn complexities-of-robots [file n]
  (let [txt (c/get-input file)
        input (re-seq #"\d+\w" txt)
        nums (c/extract-numbers txt)]
    (->> input
         (map #(find-best-path % n))
         (map * nums)
         (c/sum))))

(defn part1 [file]
  (complexities-of-robots file 2))

(defn part2 [file]
  (complexities-of-robots file 25))

