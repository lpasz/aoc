(ns aoc20.day06
  (:require [clojure.string :as str]))

(def example (c/get-input "example.txt"))
(def input (c/get-input "input.txt"))

(defn anyone-yes [text]
  (->> (str/split text #"\n\n")
       (map #(str/replace % #"\n" ""))
       (map (comp count set seq))
       (apply +)))

(= 11 (anyone-yes example))

(anyone-yes input)

(defn contained-in-every [coll]
  (filter (fn [c]
            (every? #((set (seq %1)) c) (rest coll)))
          (first coll)))

(defn every-yes [text]
  (->> (str/split text #"\n\n")
       (map #(str/split % #"\n"))
       (map (comp count contained-in-every))
       (apply +)))

(= 6 (every-yes example))

(every-yes input)
