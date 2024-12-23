(ns aoc23.day01
  "Trebuchet?!"
  (:require [clojure.string :as str]
            [core :as c]))

(def digits
  "Map the text of digit to it's number"
  {"one" "1"
   "two" "2"
   "three" "3"
   "four" "4"
   "five" "5"
   "six" "6"
   "seven" "7"
   "eight" "8"
   "nine" "9"})

(defn- create-digits-re [digits]
  (->> (keys digits)
       (cons "\\d")
       (str/join "|")
       (re-pattern)))

(def re-digits (create-digits-re digits))
(def reverse-re-digits
  (->> (c/map-key str/reverse digits)
       (into {})
       (create-digits-re)))

(defn- to-number-digits [mixed-digits]
  (map #(get digits % %) mixed-digits))

(defn- find-first [text]
  (re-find re-digits text))

(defn- find-last [text]
  (->> (str/reverse text)
       (re-find reverse-re-digits)
       (str/reverse)))

(defn part1 [input]
  (->> (str/split input #"\n")
       (map #(re-seq #"\d" %))
       (map #(str/join [(first %) (last %)]))
       (map c/parse-int)
       (apply +)))

(defn part2 [inp]
  (->> (str/split-lines inp)
       (map (juxt find-first find-last))
       (map to-number-digits)
       (map #(str/join "" %))
       (map c/parse-int)
       (apply +)))

