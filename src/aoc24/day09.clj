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
  (->> (re-seq #"." (c/get-input file))
       (map parse-long)
       (partition 2 2 [0])
       (map-indexed (fn [idx [used free]] [(take used (repeat idx)) (take free (repeat \.))]))
       (flatten)
       (vec)
       (free-space-right)
       (c/reject #(= \. %))
       (map-indexed #(* %1 %2))
       (c/sum)))

(defn free-space-in-blocks-right [acc]
  (loop [itms (reverse acc)
         acc acc]
    (if (empty? itms)
      acc
      (let [[mv-id mv-used] (first itms)
            acc (->> acc
                     (reduce (fn [[acc moved] [id used free]]
                               (cond
                                 ; if already been moved, just free the space
                                 (and moved (= mv-id id))
                                 [(conj acc [\. 0 (+ free used)]) true]
                                 ; if has not moved so far, moves to the same space
                                 (and (not moved) (= mv-id id))
                                 [(conj acc [id used free]) true]
                                 ; if not yet moved and found a valid space move to that space
                                 (and (not moved) (not= id mv-id) (>= free mv-used))
                                 [(conj acc [id used 0] [mv-id mv-used (- free mv-used)]) true]
                                 ; otherwise just keep going
                                 :else
                                 [(conj acc [id used free]) moved]))
                             [[] false])
                     (first))]
        (recur (rest itms) acc)))))

(defn part2 [file]
  (->> (re-seq #"." (c/get-input file))
       (map parse-long)
       (partition 2 2 [0])
       (map-indexed (fn [idx [used free]] [idx used free]))
       (vec)
       (free-space-in-blocks-right)
       (map (fn [[id used free]] [(take used (repeat id)) (take free (repeat \.))]))
       (flatten)
       (map-indexed vector)
       (c/reject #(= \. (second %)))
       (map (fn [[a b]] (* a b)))
       (c/sum)))

