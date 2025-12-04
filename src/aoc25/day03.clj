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

(defn next-combinations [curr-bat next-bat]
  (->> (for [idx (range 0 (count curr-bat))]
         (conj (c/remove-at idx curr-bat) next-bat))
       (c/then [coll] (conj coll curr-bat))
       (c/max-by c/digits-to-number)))

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

(defn max-jolts [file num-of-batteries]
  (let [lines (parse-input file)]
    (->> (for [line lines]
           (max-jolt line num-of-batteries))
         (map #(apply str %))
         (map parse-long)
         (c/sum))))

(defn part1 [file] (max-jolts file 2))
(defn part2 [file] (max-jolts file 12))

