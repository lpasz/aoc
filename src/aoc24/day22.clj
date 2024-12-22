(ns aoc24.day22
  (:require [core :as c]))

(defn part1 [file] :not-implemented)

(defn part2 [file] :not-implemented)

(defn prune [sn]
  (mod sn 16777216))

;(prune 100000000) ;; 16113920

(defn mix [sn n]
  (bit-xor sn n))

;(mix 42 15) ;; 37

(defn step1 [sn]
  (prune (mix sn (* sn 64))))

(defn step2 [sn]
  (prune (mix sn (Math/round (Math/floor (/ sn 32.0))))))

(defn step3 [sn]
  (prune (mix sn (* sn 2048))))

(defn next-secret-number
  ([n sn] (loop [n n sn [sn]]
            (if (zero? n)
              sn
              (recur (dec n) (conj sn (next-secret-number (peek sn)))))))
  ([sn]
   (->> sn
        (step1)
        (step2)
        (step3))))

(defn part1 [file]
  (->> (slurp file)
       (c/extract-numbers)
       (map #(next-secret-number 2000 %))
       (map peek)
       (c/sum)))
