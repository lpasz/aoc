(ns aoc24.day23
  (:require [core :as c]
            [clojure.string :as s]
            [clojure.set :as set]))

(defn parse-input [file]
  (let [node-connections (->> (re-seq #"\w+" (c/get-input file))
                              (partition 2))
        neighbours (->> node-connections
                        (reduce (fn [acc [p1 p2]]
                                  (->
                                   acc
                                   (update p1 (fn [conn] (conj (or conn #{}) p2)))
                                   (update p2 (fn [conn] (conj (or conn #{}) p1)))))
                                {}))]
    {:nodes (set (map first node-connections))
     :neighbours neighbours}))

(defn part1 [file]
  (let [{:keys [nodes neighbours]} (parse-input file)]
    (->> (for [i nodes]
           (for [j (neighbours  i)]
             (for [k (neighbours j)
                   :when (and (not= i j)
                              (not= j k)
                              (not= k j))]
               (let [si (neighbours i)
                     sj (neighbours j)
                     sk (neighbours k)]
                 (when (and (si j) (si k)
                            (sj i) (sj k)
                            (sk i) (sk j))
                   (set [i j k]))))))
         (flatten)
         (filter #(= (count %) 3))
         (c/reject nil?)
         (set)
         (filter (partial some #(s/starts-with? % "t")))
         (count))))

(defn vector-intersection
  "Finds the intersection between two vectors.
  Assumes no duplicate elements in either vector."
  [vec-1 vec-2]
  (apply vector (set/intersection (set vec-1) (set vec-2))))

(defn bron-kerbosch
  "Performs the Bron-Kerbosch iterative algorithm."
  ([nodes neighbours] (bron-kerbosch [] nodes [] neighbours))
  ([r p x neighbours]
   (if (and (empty? p) (empty? x))
     [(set r)]
     (loop [p p
            x x
            result []]
       (if (empty? p)
         result
         (let [v (first p)
               nv (neighbours v)
               result (into result
                            (bron-kerbosch (cons v r)
                                           (vector-intersection p nv)
                                           (vector-intersection x nv)
                                           neighbours))
               p (rest p)
               x (cons v x)]
           (recur p x result)))))))

(defn part2 [file]
  (let [{:keys [nodes neighbours]} (parse-input file)]
    (->> (bron-kerbosch nodes neighbours)
         (sort-by count >)
         (first)
         (sort)
         (s/join ","))))
