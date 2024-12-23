(ns aoc22.day01 (:require [clojure.string :as s]))

(defn- elf-total-calories [text]
  (->> (s/split text #"\n\n")
       (map (fn [elf] (->> (s/split-lines elf)
                           (map parse-long)
                           (apply +))))
       (sort >)))

(defn part1 [text] (->> (elf-total-calories text) (first)))

(defn part2 [text] (->> (elf-total-calories text) (take 3) (apply +)))

