(ns aoc23.day09
  "Mirage Maintenance"
  (:require [clojure.string :as str]
            [core :as c]))

(defn- parse-input [inp]
  (->> (str/split-lines inp)
       (map #(str/split % #" "))
       (map #(map c/parse-int %))))

(defn- diff-in-between-steps [coll]
  (->> (partition 2 1 coll)       
       (map (fn [[x y]] (- y x)))))

(defn- predict-next [coll]
  (loop [coll coll
         next-value (last coll)]
    (let [diff (diff-in-between-steps coll)]
      (if (every? zero? diff)
        next-value
        (recur diff (+ next-value (last diff)))))))

(defn part1 [inp]
  (->> (parse-input inp)
       (map predict-next)
       (c/sum)))

(defn part2 [inp]
  (->> (parse-input inp)
       (map reverse)
       (map predict-next)
       (c/sum)))

