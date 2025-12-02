
(ns aoc25.day01
  (:require [core :as c]))

(defn parse-input [file]
  (->> (re-seq #"L|R|\d+" (c/get-input file))
       (partition 2)
       (map (fn [[lr n]] [({"L" :left "R" :right} lr) (c/parse-int n)]))))

(def table (-> (->> (c/inc-range 0 99)
                    (map (fn [i] [i {:left (dec i) :right (inc i)}]))
                    (into {}))
               (assoc 0 {:left 99 :right 1})
               (assoc 99 {:left 98 :right 0})))

(defn tick-by-tick [dir remain curr cnt]
  (loop [curr curr
         remain remain
         cnt cnt]
    (if (zero? remain)
      [curr cnt]
      (let [ncurr (dir (table curr))]
        (recur ncurr (dec remain) (if (zero? ncurr) (inc cnt) cnt))))))

(defn part1 [file]
  (loop [moves (parse-input file)
         curr 50
         cnt 0]
    (let [head (first moves)
          tail (rest moves)]
      (if (empty? head)
        cnt
        (let [[dir n] head
              [result _] (tick-by-tick dir n curr 0)]
          (if (zero? result)
            (recur tail result (inc cnt))
            (recur tail result cnt)))))))

(defn part2 [file]
  (loop [moves (parse-input file)
         curr 50
         cnt 0]
    (let [head (first moves)
          tail (rest moves)]
      (if (empty? head)
        cnt
        (let [[dir n] head
              [curr cnt] (tick-by-tick dir n curr cnt)]
          (recur tail curr cnt))))))

