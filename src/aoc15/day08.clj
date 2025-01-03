(ns aoc15.day08
  (:require [core :as c]
            [clojure.string :as s]))


(defn calc [n]
  [(count (str \" n \"))
   (count n)])

(calc "")
(calc "abc")

(parse-long (first (rest (first (re-seq #"\\x(\d\d?)" "arsta\\x2")))))


(s/split-lines (c/get-input "input.txt"))

(char (c/hex->long 27))
