(ns aoc15.day08
  (:require [core :as c]
            [clojure.string :as s]))

(defn- cnt1 [t]
  (-> t
      (s/replace "\\\"" "a")
      ;; this is order dependent
      (s/replace "\\\\" "a")
      (s/replace #"\\x[0123456789abcdef]{2}" "a")
      (count)
      (dec)
      (dec)))

(defn part1 [file]
  (->> (c/get-input file)
       (s/split-lines)
       (map (fn [line] (- (count line) (cnt1 line))))
       (c/sum)))

(defn- cnt2 [t]
  (-> t
      (s/replace "\\" "\\\\")
      (s/replace "\"" "\\\"")
      (s/replace #"\\x[0123456789abcdef]{2}" "\\\\xaa")
      (count)
      (inc)
      (inc)))

(defn part2 [file]
  (->> (c/get-input file)
       (s/split-lines)
       (map (fn [line] (- (cnt2 line) (count line))))
       (c/sum)))
