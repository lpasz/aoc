(ns aoc24.day13
  (:require [core :as c]
            [clojure.string :as s]))

(defn calc [[[ax ay] [bx by] [px py]]]
  (for [a (range 0 (+ 1 (max (quot px ax) (quot px bx))))
        b (range 0 (+ 1 (max (quot py ay) (quot py by))))
        :when (and (= px (+ (* a ax) (* b bx)))
                   (= py (+ (* a ay) (* b by))))]
    (+ (* 3 a) (* b 1))))

(defn part1 [file]
  (->> (s/split (slurp file) #"\n\n")
       (map #(map parse-long (re-seq #"\d+" %)))
       (map #(partition 2 %))
       (mapcat calc)
       (c/sum)))

(defn add [[a b p]]
  [a b (mapv #(+ 10000000000000 %) p)])

(defn part2 [file]
  (->> (s/split (slurp file) #"\n\n")
       (map #(map parse-long (re-seq #"\d+" %)))
       (map #(partition 2 %))
       (map #(map vec %))
       (map add)
       (map c/transpose)
       (map c/invert-matrix)
       (filter #(every? integer? %))
       (map (fn [[a b]] (+ (* a 3) b)))
       (c/sum)))

