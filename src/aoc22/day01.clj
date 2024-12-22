(ns aoc22.day01 (:require [clojure.string :as s]))

(def inp (slurp "../aoc22/inputs/day-01/inp.txt"))

(defn- elf-total-calories [text]
  (->> (s/split text #"\n\n")
       (map (fn [elf] (->> (s/split-lines elf)
                           (map parse-long)
                           (apply +))))
       (sort >)))

(defn max-elf-cal [text] (->> (elf-total-calories text) (first)))

(defn top-3-elf-cal [text] (->> (elf-total-calories text) (take 3) (apply +)))

(max-elf-cal inp) ;; 69528
(top-3-elf-cal inp) ;; 206152
