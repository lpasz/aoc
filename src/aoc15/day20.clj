(ns aoc15.day20
  (:require [core :as c]))

(defn all-sums [safe]
  (reduce (fn [acc e]
            (reduce (fn [acc n]
                      (if (contains? acc n)
                        (update acc n #(+ (* 10 e) %))
                        (assoc acc n (* 10 e))))
                    acc
                    (range e (inc safe) e)))
          (sorted-map)
          (c/inc-range 1 safe)))

(defn part1 [file]
  (let [input (c/get-input file)
        number (c/parse-int input)
        safe-ceil (quot number 10)]
    (->> (all-sums safe-ceil)
         (filter (fn [[_k v]] (>= v number)))
         (ffirst))))

(defn all-sums2 [safe]
  (reduce (fn [acc e]
            (reduce (fn [acc n]
                      (if (contains? acc n)
                        (update acc n #(+ (* 11 e) %))
                        (assoc acc n (* 11 e))))
                    acc
                    (take 50 (range e (inc safe) e))))

          (sorted-map)
          (c/inc-range 1 safe)))

(defn part2 [file]
  (let [input (c/get-input file)
        number (c/parse-int input)
        safe-ceil (quot number 10)]
    (->> (all-sums2 safe-ceil)
         (filter (fn [[_k v]] (>= v number)))
         (ffirst))))

