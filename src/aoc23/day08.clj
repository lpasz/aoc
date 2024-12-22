(ns aoc23.day08
  "Haunted Wasteland"
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

(defn- symbol-to-left-right [inp]
  (->> (str/split-lines inp)
       (map #(re-seq #"[A-Z|\d]+" %))
       (map (fn [[key left right]]
              [key {\L left \R right}]))
       (into (sorted-map))))

(defn- parse-input [input]
  (let [[left-right rest] (str/split input #"\n\n")
        left-right (cycle (seq left-right))
        symbol-to-left-right-map (symbol-to-left-right rest)]
    [left-right symbol-to-left-right-map]))

(defn- steps-to-ZZZ [[lr-cycle symbol-to-left-right-map]]
  (loop [current-point (ffirst symbol-to-left-right-map)
         steps 0]
    (if (= "ZZZ" current-point)
      steps
      (let [current-step (nth lr-cycle steps)
            next-point (get-in symbol-to-left-right-map [current-point current-step])]
        (recur next-point (inc steps))))))

(defn- steps-to-XXZ [lr-cycle symbol-to-left-right-map current-point]
  (loop [current-point current-point
         step 0]
    (if (str/ends-with? current-point "Z")
      step
      (recur ((symbol-to-left-right-map current-point) (nth lr-cycle step))
             (inc step)))))

(defn- get-starting-positions [symbol-to-left-right-map]
  (->> symbol-to-left-right-map
       (keys)
       (filter #(str/ends-with? % "A"))))

(defn part1 [inp]
  (->> (parse-input inp)
       (steps-to-ZZZ)))

(defn part2 [inp]
  (let [[left-right symbol-to-left-right-map] (parse-input inp)]
    (->> (get-starting-positions symbol-to-left-right-map)
         (map #(steps-to-XXZ left-right symbol-to-left-right-map %))
         (reduce math/lcm))))

