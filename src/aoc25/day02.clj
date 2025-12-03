(ns aoc25.day02
  (:require [core :as c]))

(defn parse-input [file]
  (->> (c/get-input file)
       (c/extract-natural-numbers)
       (partition 2)))

(defn repetition-in-range [[start-at ends-at] patterns]
  (->> (c/inc-range start-at ends-at)
       (keep (fn [number]
               (let [snum (str number)
                     founds (->> patterns (map #(re-find % snum)) (map first) (set))]
                 (when (founds snum)
                   number))))))

(defn part1 [file]
  (->> (parse-input file)
       (mapcat #(repetition-in-range % [#"(.+)\1"]))
       (c/sum)))

(defn part2 [file]
  (->> (parse-input file)
       (mapcat #(repetition-in-range % [#"(.+?)\1+" #"(.+)\1+"]))
       (c/sum)))

