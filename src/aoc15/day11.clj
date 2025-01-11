(ns aoc15.day11
  (:require [core :as c]
            [clojure.string :as s]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def letter-trios (into (sorted-set) (map vec (partition 3 1 alphabet))))

(def next-letter
  (->>  (cycle alphabet)
        (partition 2 1)
        (take 26)
        (map (fn [[from to]] [from [(if (> (int from) (int to)) 1 0) to]]))
        (into {})))

(defn next-word [word]
  (let [cnt (count word)]
    (loop [idx 1
           acc 1
           word word]
      (if (zero? acc)
        word
        (let [[jump letter] (next-letter (word (- cnt idx)))]
          (recur (+ idx jump) jump (update word (- cnt idx) (fn [_] letter))))))))
(count (vec "abcdefgh"))

(next-word (vec "abcdefgh"))

(defn contains-valid-letter-trio? [word]
  (->> (partition 3 1 word)
       (map vec)
       (some letter-trios)))

(defn contains-repeated-letter? [word]
  (loop [i 0
         pairs (partition 2 1 word)]
    (if (empty? pairs)
      (>= i 2)
      (let [[a b] (first pairs)
            r (rest pairs)]
        (if (= a b)
          (recur (inc i) (rest r))
          (recur i r))))))

(defn valid-password? [word]
  (and (contains-valid-letter-trio? word)
       (not (some #{\i \l \o} word))
       (contains-repeated-letter? word)))

(defn next-valid-password [value]
  (->> (vec value)
       (iterate next-word)
       (filter valid-password?)))

(defn part1 [file]
  (->> (next-valid-password file)
       (first)
       (apply str)))

(defn part2 [file]
  (->> (next-valid-password file)
       (second)
       (apply str)))

