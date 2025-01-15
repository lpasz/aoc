(ns aoc15.day16
  (:require [core :as c]))

(def aunt-sue {:children 3
               :cats 7
               :samoyeds 2
               :pomeranians 3
               :akitas 0
               :vizslas 0
               :goldfish 5
               :trees 3
               :cars 2
               :perfumes 1})

(defn- parse-input [file]
  (->> (re-seq #"Sue (\d+): (.*)" (c/get-input file))
       (map (fn [[_ sue-number items]]
              [(parse-long sue-number)
               (->> (re-seq #"(\w+): (\d+)" items)
                    (map rest)
                    (map (c/fnvec keyword parse-long))
                    (into (sorted-map)))]))))

(defn part1 [file]
  (->> (parse-input file)
       (filter (fn [[_sue-number sue-items]]
                 (->> (keys sue-items)
                      (map #(= (get sue-items % :nope)
                               (get aunt-sue % :not-here)))
                      (every? true?))))
       (ffirst)))

(defn part2 [file]
  (->> (parse-input file)
       (filter (fn [[_sue-number sue-items]]
                 (->> (keys sue-items)
                      (map (fn [k]
                             (if (and (contains? sue-items k)
                                      (contains? aunt-sue k))
                               (case k
                                 :cats (< (aunt-sue k) (sue-items k))
                                 :trees (< (aunt-sue k) (sue-items k))
                                 :pomeranians (> (aunt-sue k) (sue-items k))
                                 :goldfish (> (aunt-sue k) (sue-items k))
                                 (= (sue-items k) (aunt-sue k)))
                               false)))
                      (every? true?))))
       (ffirst)))

(part2 "input.txt")
