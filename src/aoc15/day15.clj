(ns aoc15.day15
  (:require [core :as c]))

(defn possible-teaspons [n]
  (->> (repeat (c/inc-range 1 100))
       (take n)
       (c/cartesian-product)
       (filter #(= 100 (c/sum %)))))

(defn attempt-all-spoon-variations [file]
  (let [recipes (->> (c/get-input file)
                     (re-seq #"(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)")
                     (map rest)
                     (map (c/fnvec identity parse-long parse-long parse-long parse-long parse-long)))
        teaspons (possible-teaspons (count recipes))]

    (->> (for [spoon teaspons]
           (->> (map (fn [[_name capacity durability flavor texture calories] amount]
                       [amount
                        (* amount calories)
                        (* amount capacity)
                        (* amount durability)
                        (* amount flavor)
                        (* texture amount)]) recipes spoon)
                (c/transpose)))
         (map (fn [[num-of-spoon calories & tail]] [num-of-spoon
                                                    (c/sum calories)
                                                    (->> tail
                                                         (map c/sum)
                                                         (map #(if (neg? %) 0 %))
                                                         (c/product))])))))

(defn part1 [file]
  (->> (attempt-all-spoon-variations file)
       (c/max-by peek)))

(defn part2 [file]
  (->> (attempt-all-spoon-variations file)
       (filter #(= 500 (second %)))
       (c/max-by peek)))

