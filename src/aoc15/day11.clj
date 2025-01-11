(ns aoc15.day11
  (:require [core :as c]))

(def letter-trios (into (sorted-set) (map vec (partition 3 1 c/alphabet))))

(def next-letter (c/inc-remap-with-wrap c/alphabet))

(defn next-word [vec-word] (c/inc-with-remap vec-word next-letter))

(defn contains-valid-letter-trio? [word]
  (->> (partition 3 1 word)
       (map vec)
       (some letter-trios)))

(defn contains-repeated-letter? [word]
  (loop [non-overlapping-pair-cnt 0
         [[p1 p2] & tail :as pairs] (partition 2 1 word)]
    (cond (empty? pairs) (>= non-overlapping-pair-cnt  2)
          (= p1 p2) (recur (inc non-overlapping-pair-cnt) (rest tail))
          :else (recur non-overlapping-pair-cnt   tail))))

(defn valid-password? [word]
  (and (contains-valid-letter-trio? word)
       (not-any? #{\i \l \o} word)
       (contains-repeated-letter? word)))

(defn next-valid-password [value]
  (->> (vec value)
       (iterate next-word)
       (filter valid-password?)))

(defn part1 [file]
  (->> (next-valid-password file)
       (first)
       (apply str)))

(defn part2 [file]
  (->> (next-valid-password file)
       (second)
       (apply str)))
