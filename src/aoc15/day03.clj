(ns aoc15.day03
  (:require [core :as c]))

(defn part1 [file]
  (let [directions (c/movements (c/get-input file))]
    (count (set (loop [dirs directions
                       curr-coord [0 0]
                       visited []]
                  (if (empty? dirs)
                    visited
                    (let [dir (first dirs)
                          next-coord (dir (c/directions curr-coord))]
                      (recur (rest dirs) next-coord (conj visited curr-coord)))))))))

(defn part2 [file]
  (let [directions (c/movements (c/get-input file))]
    (count (set (loop [dirs directions
                       [coord1 coord2] [[0 0] [0 0]]
                       visited []]
                  (if (empty? dirs)
                    visited
                    (let [dir (first dirs)
                          next-coord (dir (c/directions coord1))]
                      (recur (rest dirs) [coord2 next-coord] (conj visited coord1)))))))))



