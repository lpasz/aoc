(ns aoc15.day06
  (:require [core :as c]))

(defn parse-input [file]
  (->> (c/get-input file)
       (re-seq #"(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)")
       (map rest)
       (map (c/fnvec {"turn on" :on "turn off" :off "toggle" :toggle}
                     parse-long
                     parse-long
                     parse-long
                     parse-long))))

(defn light-board [value]
  (->> (range 1000)
       (mapv (fn [_] (->> (range 1000)
                          (mapv (fn [_] value)))))))

(defn make-coords [x1 y1 x2 y2]
  (for [x (range (min x1 x2) (inc (max x1 x2)))
        y (range (min y1 y2) (inc (max y1 y2)))]
    [x y]))

(defn act-on-lights [action-fn acc coords]
  (reduce (fn [acc [x y]]
            (let [value (get-in acc [y x])]
              (assoc-in acc [y x] (action-fn value))))
          acc
          coords))

(defn act-toggle [action]
  (case action
    :on (fn [_] true)
    :off (fn [_] false)
    :toggle (fn [v] (not v))))

(defn apply-act-on-lights [file board-value act-fn]
  (let [steps (parse-input file)
        lights (light-board board-value)]
    (->> steps
         (reduce (fn [acc [action x1 y1 x2 y2]]
                   (let [board (make-coords x1 y1 x2 y2)]
                     (act-on-lights (act-fn action) acc board)))
                 lights))))

(defn part1 [file]
  (->> (apply-act-on-lights file false act-toggle)
       (flatten)
       (filter true?)
       (count)))

(defn act-inc [action]
  (case action
    :on inc
    :off #(max (dec %) 0)
    :toggle (comp inc inc)))

(defn part2 [file]
  (->> (apply-act-on-lights file 0 act-inc)
       (flatten)
       (c/sum)))
