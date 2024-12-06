(ns aoc24.day06
  (:require [core :as c]))

(defn next-direction [direction]
  ({:up :->
    :-> :down
    :down :<-
    :<- :up} direction))

(defn part1 [file]
  (let [text (slurp file)
        mtx (c/to-matrix text)
        guard-coord (->> mtx
                         (filter #(= (second %) \^))
                         (ffirst))
        mtx (assoc mtx guard-coord \.)]
    (loop [guard-coord guard-coord
           visited #{guard-coord}
           direction :up]
      (let [next-coord (direction (c/directions guard-coord))
            next-block (get mtx next-coord)
            wall? (= next-block \#)
            void? (nil? next-block)]
        ;; (c/print-matrix (reduce (fn [mtx itm] (assoc mtx itm \X)) mtx visited))
        (cond wall? (recur guard-coord
                           visited
                           (next-direction direction))
              void? (count visited)
              :else (recur next-coord
                           (conj visited next-coord)
                           direction))))))

(defn part2 [file]
  (let [text (slurp file)
        mtx (c/to-matrix text)
        guard-coord (->> mtx
                         (filter #(= (second %) \^))
                         (ffirst))
        mtx (assoc mtx guard-coord \.)]
    (loop [guard-coord guard-coord
           visited #{guard-coord}
           direction :up]
      (let [next-coord (direction (c/directions guard-coord))
            next-block (get mtx next-coord)
            wall? (= next-block \#)
            void? (nil? next-block)]
        ;; (c/print-matrix (reduce (fn [mtx itm] (assoc mtx itm \X)) mtx visited))
        (cond wall? (recur guard-coord
                           visited
                           (next-direction direction))
              void? (count visited)
              :else (recur next-coord
                           (conj visited next-coord)
                           direction))))))
