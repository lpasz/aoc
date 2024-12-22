(ns aoc22.day02-small
  (:require [clojure.string :as s]))

(def inp (slurp "input.txt"))

(def ex1-points {"A X" 4 "A Y" 8 "A Z" 3
                 "B X" 1 "B Y" 5 "B Z" 9
                 "C X" 7 "C Y" 2 "C Z" 6})

(def ex2-points {"A X" 3 "A Y" 4 "A Z" 8
                 "B X" 1 "B Y" 5 "B Z" 9
                 "C X" 2 "C Y" 6 "C Z" 7})

(defn ex1 [text]
  (->> (s/split-lines text) (reduce #(+ (ex1-points %2) %1) 0)))

(defn ex2 [text]
  (->> (s/split-lines text) (reduce #(+ (ex2-points %2) %1) 0)))

(time (ex1 inp)) ;; 14531
(time (ex2 inp)) ;; 11258
