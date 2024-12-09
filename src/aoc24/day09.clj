(ns aoc24.day09
  (:require [core :as c]))

(defn free-space-right [memory]
  (let [[free-idx space] (->> memory
                              (map-indexed (fn [idx itm] [idx itm]))
                              (filter #(= \. (second %)))
                              (first))
        [used-idx mem] (->> memory
                            (map-indexed (fn [idx itm] [idx itm]))
                            (reverse)
                            (filter #(not= \. (second %)))
                            (first))]
    (if (> free-idx used-idx)
      memory
      (recur (-> memory
                 (assoc free-idx mem)
                 (assoc used-idx space))))))

(defn part1 [file]
  (->> (re-seq #"." (slurp file))
       (map parse-long)
       (partition 2 2 [0])
       (map-indexed (fn [idx [used free]]
                      [(take used (repeat idx))
                       (take free (repeat \.))]))
       (flatten)
       (vec)
       (free-space-right)
       (c/reject #(= \. %))
       (map-indexed #(* %1 %2))
       (c/sum)
       ;;
       ))
(part1 "./assets/day09/example.txt")

(defn part2 [file]
  (->> (re-seq #"." (slurp file))
       (map parse-long)
       (partition 2 2 [0])
       (map-indexed vector)
       ))
