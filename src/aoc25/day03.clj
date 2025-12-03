(ns aoc25.day03
  (:require [core :as c]
            [clojure.string :as s]))

(defn parse-input [file]
  (->> (c/get-input file)
       (s/split-lines)
       (map (fn [line]
              (map-indexed
               (fn [idx itm] [idx (c/parse-int itm)])
               (re-seq #"\d" line))))))

(parse-input "input.txt")

(defn part1 [file]
  (let [items (parse-input file)]
    (->> items
         (map (fn [line]
                (for [[idx1 i1] line
                      [idx2 i2] line
                      :when (< idx1 idx2)]
                  (c/parse-int (str i1 i2)))))
         (map #(apply max %))
         (c/sum))))

(part1 "input.txt")  ; 357

(def comb
  (->> (c/inc-range 0 11)
       (c/rotations)
       (map #(drop 1 %))
       (map sort)))

(defn next-combinations [curr-bat next-bat]
  (->> (for [idx (range 0 (count curr-bat))]
         (conj (c/remove-at idx curr-bat) next-bat))
       (c/then [coll] (conj coll curr-bat))
       (map (fn [coll] [coll (c/parse-int (apply str coll))]))
       (sort-by second >)
       (ffirst)))

(defn max-jolt [batteries num-of-batteries]
  (let [batteries (map (fn [[_ jolts]] jolts) batteries)
        curr-bat (vec (take num-of-batteries batteries))
        rem-bats (drop num-of-batteries batteries)]
    (loop [curr-bat curr-bat
           rem-bats rem-bats]
      (let [head (first rem-bats)
            tail (rest rem-bats)]
        (if (nil? head)
          curr-bat
          (recur
           (next-combinations curr-bat head)
           tail))))))

(defn part2 [file]
  (let [lines (parse-input file)]
    (->> (for [line lines]
           (max-jolt line 12))
         (map #(apply str %))
         (map c/parse-int)
         (c/sum))))

(part2 "example.txt")
