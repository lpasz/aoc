(ns aoc15.day12
  (:require [core :as c]
            [clojure.data.json :as json]))

(defn part1 [file]
  (->> (c/get-input file)
       (c/extract-numbers)
       (c/sum)))

(defn key-or-val-red? [json]
  (or (contains? json "red")
      (->> (c/filter-by-value #{"red"} json)
           (first)
           (some?))))

(defn discard-red? [json]
  (and (map? json) (key-or-val-red? json)))

(defn keep-nums-in-non-red-maps [json]
  (loop [acc 0
         [head & tail :as json] [json]]
    (cond (empty? json) acc
          (discard-red? head) (recur acc tail)
          (number? head) (recur (+ acc head) tail)
          (map? head) (recur acc (apply conj tail (vals head)))
          (vector? head) (recur acc (apply conj tail head))
          :else (recur acc tail))))

(defn part2 [file]
  (->> (c/get-input file)
       (json/read-str)
       (keep-nums-in-non-red-maps)))
