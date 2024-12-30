(ns aoc15.day02
  (:require [core :as c]))

(defn calc-ribbon [[x y z]]
  (let [areas [(* x y)
               (* y z)
               (* x z)]
        area (->> areas
                  (map #(* % 2))
                  (c/sum))]
    (+ area (apply min areas))))

(defn calc-wrap [dim]
  (let [[m1 m2] (sort dim)]
    (+ (* 2 m1) (* 2 m2))))


(defn parse-input [file]
  (->> (c/get-input file)
       (re-seq #"\d+")
       (map parse-long)
       (partition 3)))

(defn part1 [file]
  (->> (parse-input file)
       (map calc-wrap)
       (c/sum)))



(->> (parse-input "input.txt")
     (map calc-wrap)
     (c/sum))
