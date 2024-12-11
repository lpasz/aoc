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

(defn zip-stones [stones]
  (reduce (fn [acc stone]
            (update
             acc
             stone
             #(inc (or % 0))))

          {} stones))
(defn apply-blinks [file blinks]
  (let [input (->> (re-seq #"\d+" (slurp file))
                   (map parse-long)
                   (zip-stones))]
    (loop [blinks blinks stones input]
      (if (zero? blinks)
        (c/sum (vals stones))
        (recur (dec blinks)
               (reduce (fn [acc [old-stone cnt]]
                         (reduce (fn [acc new-stone]
                                   (update acc new-stone #(+ cnt (or % 0))))
                                 acc
                                 (change-stone old-stone)))
                       {}
                       stones))))))
(defn part1 [file]
  (apply-blinks file 25))

(defn part2 [file]
  (apply-blinks file 75))

