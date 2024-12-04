(ns aoc24.day04
  (:require [clojure.string :as s]
            [core :as c]))

(defn xmas-dirs [[x y]]
  [[[x y] [(+ x 1) y] [(+ x 2) y] [(+ x 3) y]]
   [[x y] [(- x 1) y] [(- x 2) y] [(- x 3) y]]
   [[x y] [x (+ y 1)] [x (+ y 2)] [x (+ y 3)]]
   [[x y] [x (- y 1)] [x (- y 2)] [x (- y 3)]]
   [[x y] [(+ x 1) (+ y 1)] [(+ x 2) (+ y 2)] [(+ x 3) (+ y 3)]]
   [[x y] [(- x 1) (- y 1)] [(- x 2) (- y 2)] [(- x 3) (- y 3)]]
   [[x y] [(+ x 1) (- y 1)] [(+ x 2) (- y 2)] [(+ x 3) (- y 3)]]
   [[x y] [(- x 1) (+ y 1)] [(- x 2) (+ y 2)] [(- x 3) (+ y 3)]]])

(defn part1 [file]
  (let [mtx (c/to-matrix (slurp file))]
    (->> mtx
         (mapcat (fn [[coord _]]
                   (->> coord
                        (xmas-dirs)
                        (map (fn [coords] (reduce str (map mtx coords)))))))
         (filter #{"XMAS" "SAMX"})
         (count)
         ;; it double because it scans all twice to cover all directions
         (#(/ % 2)))))

(defn x-mas [[x y]]
  [[(inc x) (inc y)]
   [(dec x) (dec y)]
   [(dec x) (inc y)]
   [(inc x) (dec y)]
   [x y]])

(defn part2 [file]
  (let [mtx (c/to-matrix (slurp file))]
    (->> mtx
         (keep (fn [[coord letter]]
                 (when (= letter \A)
                   (->> coord
                        (x-mas)
                        (map mtx)
                        (frequencies)))))

         (filter #{{\M 2 \S 2 \A 1}})
         (count))))

