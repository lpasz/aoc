(ns aoc22.day04
  (:require [clojure.set :as set]
            [clojure.string :as s]))

(defn count-schedule-overlap [text overlap?]
  (->> (s/split text #",|\n|-")
       (map #(Integer/parseInt %))
       (partition 4)
       (map (fn [[x1 x2 y1 y2]] [(into #{} (range x1 (inc x2)))
                                 (into #{} (range y1 (inc y2)))]))
       (reduce (fn [acc [range1 range2]] (if (overlap? range1 range2)
                                           (inc acc)
                                           acc)) 0)))

(defn fully-overlap? [range1 range2]
  (or (set/subset? range1 range2) (set/subset? range2 range1)))

(defn any-overlap? [range1 range2]
  (not-empty (set/intersection range1 range2)))

(defn part1 [text]
  (count-schedule-overlap text fully-overlap?))

(defn part2 [text]
  (count-schedule-overlap text any-overlap?))

