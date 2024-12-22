(ns aoc24.day22
  (:require [core :as c]))

(defn prune ^long [^long secret-num]
  (mod secret-num 16777216))

(defn mix ^long [^long secret-num ^long n]
  (bit-xor secret-num n))

(defn step1 ^long [^long secret-num]
  (prune (mix secret-num (* secret-num 64))))

(defn step2 ^long [^long secret-num]
  (prune (mix secret-num (Math/round (Math/floor (/ secret-num 32.0))))))

(defn step3 ^long [^long secret-num]
  (prune (mix secret-num (* secret-num 2048))))

(defn next-secret-number ^long [^long secret-number]
  (->> secret-number
       (step1)
       (step2)
       (step3)))

(defn secret-number-after-nth ^long [^long n ^long secret-num]
  (loop [n n secret-num secret-num]
    (if-not (zero? n)
      (recur (dec n) (next-secret-number secret-num))
      secret-num)))

(defn prices-on-next-nth ^clojure.lang.PersistentVector [^long n ^long secret-num]
  (loop [n n
         secret-num secret-num
         prices []]
    (let [prices (conj prices (mod secret-num 10))]
      (if (zero? n)
        prices
        (recur (dec n) (next-secret-number secret-num) prices)))))

(defn part1 [file]
  (->> (slurp file)
       (c/extract-numbers)
       (pmap #(secret-number-after-nth 2000 %))
       (c/sum)))

(defn get-prices [^long n nums]
  (pmap #(prices-on-next-nth n %) nums))

(defn get-price-changes [prices]
  (pmap #(->> (partition 2 1 %)
              (map (fn [[^long p1 ^long p2]] [(- p2 p1) p2]))) prices))
(defn get-price-after-4-changes [prices]
  (->> prices
       (pmap #(->> (partition 4 1 %)
                   (map (fn [[[c1 _] [c2 _] [c3 _] [c4 price]]] [price [c1 c2 c3 c4]]))
                   (reduce (fn [acc [price changes]]
                             (if (acc changes)
                               acc
                               (assoc acc changes price))) {})
                   (map identity)))))

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

(time (part1 "./assets/aoc24/day22/input.txt"))
(time (part2 "./assets/aoc24/day22/input.txt"))
