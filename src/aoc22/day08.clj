(ns aoc22.day08
  (:require [clojure.string :as s]))

(defn tree-pos-and-height [lines]
  (->>  lines
        (map-indexed (fn [x line]
                       (->> (s/split line #"")
                            (map-indexed (fn [y height]
                                           [x y (Integer/parseInt height)])))))))

(defn parse-trees [text]
  (->> (s/split-lines text)
       (tree-pos-and-height)
       (flatten)
       (partition 3)
       (reduce (fn [acc [x y itm]] (assoc acc [x y] itm)) (sorted-map))))

(def towards {:top   (fn [[x y]] [(dec x) y]) :left  (fn [[x y]] [(inc x) y])
              :down  (fn [[x y]] [x (dec y)]) :right (fn [[x y]] [x (inc y)])})

(defn tree-stats [pos height trees direction]
  (loop [pos pos score 0]
    (let [next-pos ((towards direction) pos)
          next-tree (trees next-pos)]
      (cond (nil? next-tree) {:visible? true :scenic-score score}
            (>= next-tree height) {:visible? false :scenic-score (inc score)}
            :else (recur next-pos (inc score))))))

(defn visible? [pos height trees]
  (some #(:visible? (tree-stats pos height trees %1)) [:top :down :left :right]))

(defn count-visibles [trees]
  (->> trees
       (filter (fn [[pos height]] (visible? pos height trees)))
       (count)))

(defn part1 [text]
  (->> (parse-trees text)
       (count-visibles)))

(defn scenic-score [pos height trees]
  (reduce #(* %1 (:scenic-score (tree-stats pos height trees %2)))
          1
          [:top :down :left :right]))

(defn max-scenic-score [trees]
  (->> trees
       (map (fn [[pos height]] (scenic-score pos height trees)))
       (apply max)))

(defn part2 [text]
  (->> (parse-trees text)
       (max-scenic-score)))

