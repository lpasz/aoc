(ns aoc15.day10
  (:require [core :as c]))

(defn look-say [s]
  (->> (partition-by identity s)
       (mapcat #(vector (count %) (first %)))
       (apply str)))

(defn look-say-rounds [n s]
  (loop [n n
         state s]
    (if (zero? n)
      state
      (recur (dec n) (look-say state)))))

(defn part1 [file]
  (count (look-say-rounds 40 (c/get-input file))))

(defn part2 [file]
  (count (look-say-rounds 50 (c/get-input file))))

