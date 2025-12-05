(ns aoc25.day05
  (:require [core :as c]
            [clojure.string :as str]))

(defn parse [file-path]
  (let [[fresh-id-rng ids] (-> file-path
                               (c/get-input)
                               (str/split  #"\n\n"))
        fresh-id-rng (->> (c/extract-natural-numbers fresh-id-rng)
                          (partition 2)
                          (map sort))]
    [fresh-id-rng (c/extract-natural-numbers ids)]))

(defn part1 [file-path]
  (let [[fresh-id-rng ids] (parse file-path)]
    (->> ids
         (filter (fn [id] (some (fn [[n1 n2]] (<= n1 id n2)) fresh-id-rng)))
         (count))))

(defn part2 [file-path]
  (let [[fresh-id-rng _ids] (parse file-path)]
    (->> fresh-id-rng
         (sort-by first)
         (reduce (fn [acc [n1 n2]]
                   (if (empty? acc)
                     [[n1 n2]]
                     (let [[m1 m2] (first acc)
                           tail (rest acc)]
                       (if (<= m1 n1 m2)
                         (conj tail [(min n1 m1) (max n2 m2)])
                         (conj tail [m1 m2] [n1 n2])))))
                 '())
         (map (fn [[n1 n2]] (inc (- n2 n1))))
         (c/sum))))
