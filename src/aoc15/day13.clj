(ns aoc15.day13
  (:require [core :as c]))

(defn parse-input [file additional]
  (->> (c/get-input file)
       (re-seq #"(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\.")
       (reduce (fn [acc [_ person1 gain-lose quantity person2]]
                 (update acc person1
                         (fn [acc]
                           (assoc acc person2
                                  (if (= "gain" gain-lose)
                                    (parse-long quantity)
                                    (- (parse-long quantity))))))) {})
       (c/then [graph]
               (->> (keys graph)
                    (reduce (fn [acc k1]
                              (let [happiness (graph k1)
                                    happiness (reduce (fn [acc [k2 v]]
                                                        (assoc acc
                                                               k2
                                                               (+ ((graph k2) k1) v)))
                                                      additional
                                                      happiness)]
                                (assoc acc k1 happiness)))
                            {})))))

(defn part1 [file]
  (let [graph (parse-input file {})]
    (apply max (c/visit-all-cyclic-distances graph "Alice"))))

(defn part2 [file]
  (let [graph (parse-input file {"Myself" 0})
        myself (->> (keys graph) (map #(vector % 0)) (into {}))
        graph (assoc graph "Myself" myself)]
    (apply max (c/visit-all-cyclic-distances graph "Alice"))))
