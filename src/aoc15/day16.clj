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
       (first)))
