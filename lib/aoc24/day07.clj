(ns aoc24.day07
  (:require [clojure.string :as s]
            [core :as c]))

(defn recursive-find [target remaining acc operations]
  (if-let [curr (first remaining)]
    (->> operations
         (map #(recursive-find target
                               (rest remaining)
                               (% acc curr)
                               operations))
         (reduce #(or %1 %2)))
    (= target acc)))

(defn- part [file operations]
  (->> (s/split (slurp file) #"\n")
       (map #(re-seq #"\d+" %))
       (map #(map parse-long %))
       (filter #(recursive-find (first %) (rest %) 0 operations))
       (map first)
       (c/sum)))

(defn part1 [file] (part file [+ *]))

(defn || [a b]
  (parse-long (str a b)))

(defn part2 [file]
  (part file [+ * ||]))



