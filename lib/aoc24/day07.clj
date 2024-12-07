(ns aoc24.day07
  (:require [clojure.string :as s]
            [core :as c]))

(def file "./assets/day07/example.txt")

(defn recursive-find [target remaining acc]
  (if-let [curr (first remaining)]
    (or (recursive-find target (rest remaining) (+ curr acc))
        (recursive-find target (rest remaining) (* curr acc)))
    (= target acc)))

(defn part1 [file]
  (->> (s/split (slurp file) #"\n")
       (map #(re-seq #"\d+" %))
       (map #(map parse-long %))
       (filter #(recursive-find (first %) (rest %) 0))
       (map first)
       (c/sum)
       ;;
       ))



