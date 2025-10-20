(ns aoc15.day18
  (:require [core :as c]))

(defn neighbours-on [mtx coord]
  (->> (c/around coord)
       (map mtx)
       (filter #{\#})
       (count)))

(defn keep-on [[coord value] mtx]
  (let [neighbours-on (neighbours-on mtx coord)]
    (cond (and (= \# value) (#{2 3} neighbours-on)) [coord \#]
          (and (= \. value) (#{3} neighbours-on)) [coord \#]
          :else [coord \.])))

(defn step [mtx extra]
  (->> mtx
       (map #(keep-on % mtx))
       (into {})
       (c/then [m] (merge m extra))))

(defn part1 [file]
  (->> (c/get-input file)
       (c/to-matrix)
       (iterate #(step % {}))
       (c/then [coll] (nth coll 100))
       (c/filter-by-value #{\#})
       (count)))

(defn part2 [file]
  (->> (c/get-input file)
       (c/to-matrix)
       (iterate #(step % {[0 0] \# [99 99] \# [0 99] \# [99 0] \#}))
       (c/then [coll] (nth coll 100))
       (c/filter-by-value #{\#})
       (count)))
