(ns aoc22.day03
  (:require [clojure.string :as s]
            [clojure.set :as set]))

(def priorities (into {} (mapcat (fn [i] [[(char (+ i (int \a))) (+ i 1)]
                                          [(char (+ i (int \A))) (+ i 27)]])
                                 (range 0 26))))

(defn split-in-half [text]
  (let [size (count text)
        middle (quot size 2)]
    (split-at middle (seq text))))

(defn priority-of-intersection [items]
  (->> items
       (map set)
       (apply set/intersection)
       (first)
       (priorities)))

(defn part1 [text]
  (->> (s/split-lines text)
       (reduce #(+ %1 (-> %2 split-in-half priority-of-intersection)) 0)))

(defn part2 [text]
  (->> (s/split-lines text)
       (partition 3)
       (reduce #(+ %1 (priority-of-intersection %2)) 0)))


