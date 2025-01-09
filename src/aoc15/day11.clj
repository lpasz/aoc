(ns aoc15.day11
  (:require [core :as c]
            [clojure.string :as s]))

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(def letter-trios (set (partition 3 1 alphabet)))

(def next-letter
  (->>  (cycle alphabet)
        (partition 2 1)
        (take 26)
        (map (fn [[from to]] [from [(if (> (int from) (int to)) 1 0) to]]))
        (into {})))

(defn next-word [word]
  (let [rev (reverse word)]
    (loop [acc 1
           word rev
           result '()]
      (if (zero? acc)
        [:next (apply conj result word)]
        (let [[cnt letter] (next-letter (first word))]
          (recur cnt (rest word) (conj result letter)))))))

(defn contains-valid-letter-trio? [word]
  (->> (partition 3 1 word)
       (some letter-trios)))

(defn contains-repeated-letter? [word]
  (->> (partition 2 1 word)
       (some (fn [[a b]] (= a b)))))

(defn valid-password? [word]
  (and (contains-valid-letter-trio? word)
       (not-any? #{\i \l \o} word)
       (contains-repeated-letter? word)))

(first (filter valid-password? (iterate next-word (seq "ghijklmn"))))
