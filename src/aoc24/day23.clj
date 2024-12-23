(ns aoc24.day23
  (:require [core :as c]
            [clojure.string :as s]))

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

(defn unique-trios [nodes neighbours]
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
       (c/reject nil?)
       (filter #(= (count %) 3))
       (set)))

(defn part1 [file]
  (let [{:keys [nodes neighbours]} (parse-input file)]
    (->> (unique-trios nodes neighbours)
         (filter (partial some #(s/starts-with? % "t")))
         (count))))

(defn part2 [file]
  (let [{:keys [nodes neighbours]} (parse-input file)]
    (->> (c/bron-kerbosch nodes neighbours)
         (sort-by count >)
         (first)
         (sort)
         (s/join ","))))











