(ns aoc24.day18
  (:require [core :as c]))

(defn after-n-bytes-shortest-path [n-bytes]
  (let [nums (c/extract-numbers (slurp "./assets/day18/input.txt"))
        pairs (take n-bytes (partition 2 nums))
        max-x (->> pairs (sort-by first >) (ffirst))
        max-y (->> pairs (sort-by second >) (first) (second))
        mtx (c/create-matrix 71 71 \.)
        mtx (c/add-to-matrix mtx pairs \#)
        graph (c/matrix-to-graph mtx)
        dijk   (c/dijkstra-shortest graph [0 0] [max-x max-y])]
    [(last pairs) (get dijk [max-x max-y])]))

(defn part1 []
  (second (after-n-bytes-shortest-path 1024)))

(defn part2 []
  ;; reduced via binary search manually done.
  (->> (range 2950 3000)
       (keep (fn [n] (let [[pair dist] (after-n-bytes-shortest-path n)]
                       (when (infinite? dist)
                         [n pair]))))
       (first)
       (second)))
