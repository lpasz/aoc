(ns aoc15.day13
  (:require [core :as c]))

(defn parse-input [file]
  (->> (c/get-input file)
       (re-seq #"(\w+) would (lose|gain) (\d+) happiness units by sitting next to (\w+)\.")
       (reduce (fn [acc [_ person1 gain-lose quantity person2]]
                 (update acc person1
                         (fn [acc]
                           (assoc acc person2
                                  (if (= "gain" gain-lose)
                                    (parse-long quantity)
                                    (- (parse-long quantity))))))) {})))

(defn cyclic-distances
  ([graph start]
   (cyclic-distances graph start start #{} 0))
  ([graph curr end visited acc]
   (let [i (->> (graph curr)
                (c/reject (fn [[k _]] (visited k))))]
     (if (empty? i)
       [(+ acc (get-in graph [curr end]))]
       (mapcat (fn [[k v]]
                 (cyclic-distances graph
                                  k
                                  end
                                  (conj visited curr)
                                  (+ v acc))) i)))))

(defn part1 [file]
  (let [graph (parse-input file)
        ngraph (->> (keys graph)
                    (reduce (fn [acc k1]
                              (let [happiness (graph k1)
                                    happiness (reduce (fn [acc [k2 v]]
                                                        (assoc acc
                                                               k2
                                                               (+ ((graph k2) k1) v)))
                                                      {}
                                                      happiness)]
                                (assoc acc k1 happiness)))
                            {}))]
    (apply max (cyclic-distances ngraph "Alice"))))

(defn part2 [file]
  (let [graph (parse-input file)
        ngraph (->> (keys graph)
                    (reduce (fn [acc k1]
                              (let [happiness (graph k1)
                                    happiness (reduce (fn [acc [k2 v]]
                                                        (assoc acc
                                                               k2
                                                               (+ ((graph k2) k1) v)))
                                                      {}
                                                      happiness)]
                                (assoc acc k1 (assoc happiness "Myself" 0))))
                            {}))
        myself (->> (keys ngraph) (map #(vector % 0)) (into {}))
        ngraph (-> ngraph (assoc "Myself" myself))]
    (apply max (cyclic-distances ngraph "Alice"))))
