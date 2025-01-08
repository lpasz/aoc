(ns aoc15.day09
  (:require [core :as c]))

(defn parse-input [file]
  (->> (c/get-input file)
       (re-seq #"(\w+) to (\w+) = (\d+)")
       (map rest)
       (map (c/fnvec identity identity parse-long))
       (reduce
        (fn [acc [from to distance]]
          (-> acc
              (update to (fn [dists] (update (or dists {}) from #(min (or % c/inf) distance))))
              (update from (fn [dists] (update (or dists {}) to #(min (or % c/inf) distance)))))) {})))

(defn part [file min-max]
  (let [graph (parse-input file)]
    (->> graph
         (keys)
         (mapcat #(c/visit-all-nodes-distances graph %))
         (apply min-max))))

(defn part1 [file]
  (part file min))

(defn part2 [file]
  (part file max))


