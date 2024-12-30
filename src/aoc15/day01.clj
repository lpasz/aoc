(ns aoc15.day01
  (:require [core :as c]))

(defn part1 [file]
  (->> (c/get-input file)
       (reduce (fn [acc itm] (case  itm
                               \( (update acc :open inc)
                               \) (update acc :close inc)
                               acc))
               {:open 0 :close 0})
       (c/then [{:keys [open close]}] (- open close))))

(defn part2 [file]
  (loop [acc 0
         [[idx itm] & tail] (->> file (c/get-input) (map-indexed vector))]
    (cond (= \( itm) (recur (inc acc) tail)
          (and (zero? acc) (= \) itm)) (inc idx)
          (= \) itm) (recur (dec acc) tail)
          :else (recur acc tail))))



