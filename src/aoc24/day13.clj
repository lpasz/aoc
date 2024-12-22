(ns aoc24.day13
  (:require [core :as c]
            [clojure.string :as s]))

(defn parse-input [file]
  (->> (s/split (c/get-input file) #"\n\n")
       (map #(map parse-long (re-seq #"\d+" %)))
       (map #(partition 2 %))
       (map #(map vec %))))

(defn calculate-token-cost [eqs]
  (->> eqs
       (map c/transpose)
       (map c/inverse-matrix)
       (filter #(every? integer? %))
       (map (fn [[a b]] (+ (* a 3) b)))
       (c/sum)))

(defn part1 [file]
  (->> (parse-input file)
       (calculate-token-cost)))

(defn add [[a b p]]
  [a b (mapv #(+ 10000000000000 %) p)])

(defn part2 [file]
  (->> (parse-input file)
       (map add)
       (calculate-token-cost)))

