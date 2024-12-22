(ns aoc24.day02
  (:require [clojure.string :as s]))

(defn safe? [[n1 n2 :as nums]]
  (let [op (if (> n1 n2) > <)]
    (->> nums
         (partition 2 1)
         (map (fn [[n1 n2]] (and (op n1 n2) (<= 1 (abs (- n1 n2)) 3))))
         (every? true?))))

(defn parse-input [file]
  (->>  (s/split (c/get-input file) #"\n")
        (map #(re-seq #"\d+" %))
        (map #(map parse-long %))))

(defn part1 [file]
  (->> (parse-input file)
       (filter safe?)
       (count)))

(defn remove-at [n coll]
  (concat (subvec coll 0 (dec n)) (subvec coll n (count coll))))

(defn safe-even-without-one? [numbers]
  (or (safe? numbers)
      (->> (range 0 (count numbers))
           (map #(remove-at (inc %) (vec numbers)))
           (some safe?))))
(defn part2 [file]
  (->> (parse-input file)
       (filter safe-even-without-one?)
       (count)))



