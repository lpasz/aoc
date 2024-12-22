(ns aoc24.day22
  (:require [core :as c]))

(defn prune [sn] (mod sn 16777216))

(defn mix [sn n] (bit-xor sn n))

(defn step1 [sn] (prune (mix sn (* sn 64))))

(defn step2 [sn] (prune (mix sn (Math/round (Math/floor (/ sn 32.0))))))

(defn step3 [sn] (prune (mix sn (* sn 2048))))

(defn next-secret-number [secret-number]
  (->> secret-number
       (step1)
       (step2)
       (step3)))

(defn secret-number-after-nth [n sn]
  (loop [n n sn sn prices [(mod sn 10)]]
    (if (zero? n)
      [sn prices]
      (let [secret-number (next-secret-number sn)]
        (recur (dec n) secret-number (conj prices (mod secret-number 10)))))))

(defn part1 [file]
  (->> (slurp file)
       (c/extract-numbers)
       (pmap #(secret-number-after-nth 2000 %))
       (pmap first)
       (c/sum)))

(defn get-prices [n nums]
  (->> nums
       (pmap #(secret-number-after-nth n %))
       (pmap second)))

(defn get-price-changes [prices]
  (->> prices
       (pmap #(partition 2 1 %))
       (pmap #(map (fn [[p1 p2]] [(- p2 p1) p2]) %))))

(defn get-price-after-4-changes [prices]
  (->> prices
       (pmap #(partition 4 1 %))
       (pmap #(map (fn [[[c1 _] [c2 _] [c3 _] [c4 price]]] [price [c1 c2 c3 c4]]) %))
       (pmap #(reduce (fn [acc [price changes]]
                        (if (acc changes)
                          acc
                          (assoc acc changes price))) {} %))
       (into [])))

(defn find-most-bananas-across-all-vendors [vendor-prices-after-4-changes]
  (->> vendor-prices-after-4-changes
       (c/flatten-once)
       (group-by first)
       (pmap #(->> (second %)
                   (map second)
                   (c/sum)))
       (apply max)))

(defn part2 [file]
  (->> (slurp file)
       (c/extract-numbers)
       (get-prices 2000)
       (get-price-changes)
       (get-price-after-4-changes)
       (find-most-bananas-across-all-vendors)))

