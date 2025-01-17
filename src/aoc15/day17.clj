(ns aoc15.day17
  (:require [core :as c]))

(defn binary-search
  ([total containers] (binary-search 0 0 total containers))
  ([lts cnt total [head & tail :as containers]]
   (cond (= lts total) [cnt]
         (> lts total) []
         (empty? containers) []
         :else (concat (binary-search (+ lts head) (inc cnt) total tail)
                       (binary-search lts cnt total tail)))))

(defn part1 [file]
  (->> (c/get-input file)
       (c/extract-numbers)
       (binary-search 150)
       (count)))

(defn part2 [file]
  (->> (c/get-input file)
       (c/extract-numbers)
       (binary-search 150)
       (sort)
       (partition-by identity)
       (first)
       (count)))
