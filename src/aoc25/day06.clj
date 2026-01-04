(ns aoc25.day06
  (:require [core :as c]
            [clojure.string :as str]))

(defn parse [file-path]
  (str/split-lines (c/get-input file-path)))

(defn part1 [file-path]
  (->> (parse file-path)
       (map str/trim)
       (map #(str/split % #"\s+"))
       (c/transpose)
       (map (fn [coll]
              (reduce (fn [acc itm]
                        (case itm
                          "*" (c/product acc)
                          "+" (c/sum acc)
                          (conj acc (parse-long itm))))
                      []
                      coll)))
       (c/sum)))

(defn charlist-to-int [coll]
  (parse-long (str/trim (str/join coll))))

(defn part2 [file-path]
  (->> (parse file-path)
       (map seq)
       (c/transpose)
       (map vec)
       (mapcat (fn [coll]
                 (case (peek coll)
                   \* ["*" (charlist-to-int (pop coll))]
                   \+ ["+" (charlist-to-int (pop coll))]
                   (if-let [n (charlist-to-int coll)] [n] []))))
       (partition-by string?)
       (partition 2)
       (map (fn [[[op] rest]]
              (case op
                "+" (c/sum rest)
                "*" (c/product rest))))
       (c/sum)))
