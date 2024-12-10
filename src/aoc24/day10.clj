(ns aoc24.day10
  (:require [core :as c]))

(defn all-peaks-starting-from [[coord n] path mtx]
  (if (= 9 n (count path))
    {:peak-coord coord :peak-path (conj path coord)}
    (->> coord
         (c/up-down-left-right)
         (keep (fn [ncoord] (when-let [value (get mtx ncoord)] [ncoord value])))
         (filter (fn [[ncoord nn]] (and (not (path ncoord))
                                        (= (inc n) nn))))
         (map #(all-peaks-starting-from % (conj path coord) mtx)))))

(defn part1 [file]
  (let [mtx (c/to-matrix (slurp file) #(re-seq #"." %) parse-long)
        trailheads (filter (fn [[_coord n]] (and (number? n) (zero? n))) mtx)]
    (->> trailheads
         (map #(all-peaks-starting-from % #{} mtx))
         (map flatten)
         (map #(map :peak-coord %))
         (map set)
         (map count)
         (c/sum))))

(defn part2 [file]
  (let [mtx (c/to-matrix (slurp file) #(re-seq #"." %) parse-long)
        trailheads (filter (fn [[_coord n]] (and (number? n) (zero? n))) mtx)]
    (->> trailheads
         (map #(all-peaks-starting-from % #{} mtx))
         (map flatten)
         (map #(map :peak-path %))
         (map set)
         (map count)
         (c/sum))))