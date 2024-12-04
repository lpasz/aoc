(ns aoc24.day04
  (:require [core :as c]))

(defn part1 [file]
  (let [mtx (c/to-matrix (slurp file))]
    (->> mtx
         (mapcat (fn [[coord _]]
                   (->> (c/cross-and-x coord 3)
                        (map #(reduce str (map mtx %))))))
         (filter #{"XMAS" "SAMX"})
         (count)
         ;; it double because it scans all twice to cover all directions
         (#(/ % 2)))))

(defn part2 [file]
  (let [mtx (c/to-matrix (slurp file))]
    (->> mtx
         (keep (fn [[coord letter]]
                 (when (= letter \A)
                   (->> coord
                        (c/diagonals)
                        (map mtx)))))

         (filter #{[\M \M \S \S] [\S \M \M \S] [\S \S \M \M] [\M \S \S \M]})
         (count))))

