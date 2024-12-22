(ns aoc24.day11
  (:require [core :as c]))

(defn split-even-stone [stone len]
  (let [half (quot len 2)]
    (->> stone
         (split-at half)
         (map #(apply str %))
         (map parse-long))))

(defn change-stone [stone]
  (let [str-stone (str stone)
        str-stone-cnt (count str-stone)]
    (cond (zero? stone) [1]
          (even? (count str-stone)) (split-even-stone str-stone str-stone-cnt)
          :else [(* 2024 stone)])))

(defn change-stones [stones]
  (reduce (fn [acc [old-stone cnt]]
            (reduce (fn [acc new-stone]
                      (update acc new-stone #(+ cnt (or % 0))))
                    acc
                    (change-stone old-stone)))
          {}
          stones))

(defn after-blinks [file blinks]
  (let [input (->> (re-seq #"\d+" (c/get-input file))
                   (map parse-long)
                   (reduce (fn [acc stone]
                             (update acc stone #(inc (or % 0))))
                           {}))]
    (loop [blinks blinks stones input]
      (if (zero? blinks)
        (c/sum (vals stones))
        (recur (dec blinks) (change-stones stones))))))

(defn part1 [file]
  (after-blinks file 25))

(defn part2 [file]
  (after-blinks file 75))

