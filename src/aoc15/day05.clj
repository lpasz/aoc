(ns aoc15.day05
  (:require [clojure.string :as s]
            [core :as c]))

(defn at-least-3-vowels [word]
  (->> word
       (filter #{\a \e \i \o \u})
       (count)
       (<= 3)))

(defn at-least-1-rep-char [word]
  (->> (partition 2 1 word)
       (filter (fn [[a b]] (= a b)))
       (not-empty)))

(defn does-not-contain [parts word]
  (->> parts
       (filter (partial s/index-of word))
       (empty?)))

(defn part1 [file]
  (->> (c/get-input file)
       (s/split-lines)
       (filter at-least-3-vowels)
       (filter at-least-1-rep-char)
       (filter (partial does-not-contain #{"ab" "cd" "pq" "xy"}))
       (count)))

(defn nice? [word]
  (loop [word (partition 3 1 [] word)
         trio? false
         two-pairs? false
         pairs #{}]
    (if (empty? word)
      (and trio? two-pairs?)
      (let [[a b c] (first word)]
        (recur (if (= a b c) (rest (rest word)) (rest word))
               (if (= a c) true trio?)
               (if (pairs [a b]) true two-pairs?)
               (conj pairs [a b]))))))

(defn part2 [file]
  (->> (c/get-input file)
       (s/split-lines)
       (filter nice?)
       (count)))
