(ns aoc24.day18
  (:require [core :as c]))

(defn part1 [file] :not-implemented)

(defn part2 [file] :not-implemented)

(defn matrix-to-graph [mtx]
  (->> mtx
       (keys)
       (map (fn [coord] [coord (c/up-down-left-right coord)]))
       (map (fn [[coord ncoords]] [coord (filter #(= \. (mtx %)) ncoords)]))
       (map (fn [[coord ncoords]] [coord (map (fn [coord] [coord 1]) ncoords)]))
       (map (fn [[coord ncoords]] [coord (into (sorted-map) ncoords)]))
       (into (sorted-map))))

(let [nums (c/extract-numbers (slurp "./assets/day18/input.txt"))
      pairs (take 1024 (partition 2 nums))
      max-x (->> pairs (sort-by first >) (ffirst))
      max-y (->> pairs (sort-by second >) (first) (second))
      mtx (c/create-matrix 71 71 \.)
      mtx (c/add-to-matrix mtx pairs \#)
      graph (matrix-to-graph mtx)
      dijk   (c/dijkstra-shortest graph [0 0] [max-x max-y])
      ]

  (get dijk [max-x max-y])
  ;;
  )


