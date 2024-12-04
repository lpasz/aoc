(ns aoc24.day04
  (:require [clojure.string :as s]
            [core :as c]))

(def example "./assets/day04/example.txt")
(def file "./assets/day04/input.txt")

(defn xmas-dirs [[x y]]
  [[[x y] [(+ x 1) y] [(+ x 2) y] [(+ x 3) y]]
   [[x y] [(- x 1) y] [(- x 2) y] [(- x 3) y]]
   [[x y] [x (+ y 1)] [x (+ y 2)] [x (+ y 3)]]
   [[x y] [x (- y 1)] [x (- y 2)] [x (- y 3)]]
   [[x y] [(+ x 1) (+ y 1)] [(+ x 2) (+ y 2)] [(+ x 3) (+ y 3)]]
   [[x y] [(- x 1) (- y 1)] [(- x 2) (- y 2)] [(- x 3) (- y 3)]]
   [[x y] [(+ x 1) (- y 1)] [(+ x 2) (- y 2)] [(+ x 3) (- y 3)]]
   [[x y] [(- x 1) (+ y 1)] [(- x 2) (+ y 2)] [(- x 3) (+ y 3)]]])

(let [mtx (->> (s/split (slurp file) #"\n")
               (map #(re-seq #"." %))
               (c/to-matrix))]
  (->> mtx
       (mapcat (fn [[coord _]]
                 (->> coord
                      (xmas-dirs)
                      (map (fn [coords] (reduce str (map mtx coords)))))))
       (filter #{"XMAS" "SAMX"})
       (count)))

(/ 4854 2)
