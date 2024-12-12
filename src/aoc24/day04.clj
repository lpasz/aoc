(ns aoc24.day04
  (:require [core :as c]))

(defn spell-xmas? [coords mtx]
  (= "XMAS" (->> coords (map mtx) (reduce str))))

(defn part1 [file]
  (let [mtx (c/to-matrix (slurp file))]
    (->> (keys mtx)
         (mapcat #(c/cross-and-x % 3))
         (filter #(spell-xmas? % mtx))
         (count))))

(defn part2 [file]
  (let [mtx (c/to-matrix (slurp file))]
    (->> mtx
         (c/filter-by-value #(= \A %))
         (map #(->> (first %) (c/diagonals) (map mtx)))
         (filter (set (c/rotations [\M \M \S \S])))
         (count))))
