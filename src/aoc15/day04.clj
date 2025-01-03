(ns aoc15.day04
  (:require [clojure.string :as s]
            [core :as c]))

(defn md5-starts-with? [text start-with? i]
  (-> (str text i)
      (c/md5)
      (s/starts-with? start-with?)))

(defn first-int-to-start-with [text start-with?]
  (->> (range)
       (filter (partial md5-starts-with? text start-with?))
       (first)))

(defn part1 [text]
  (first-int-to-start-with text "00000"))

(defn part2 [text]
  (first-int-to-start-with text "000000"))

