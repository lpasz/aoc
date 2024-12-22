(ns aoc24.day06
  (:require [core :as c]))

(defn next-direction [direction]
  ({:up :->
    :-> :down
    :down :<-
    :<- :up} direction))

(defn guard-coord [mtx]
  (->> mtx
       (c/filter-by-value #(= % \^))
       (ffirst)))

(defn guard-visited-spots [mtx]
  (let [guard-coord (guard-coord mtx)
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
              void? visited
              :else (recur next-coord
                           (conj visited next-coord)
                           direction))))))
(defn part1 [file]
  (->> (c/get-input file)
       (c/to-matrix)
       (guard-visited-spots)
       (count)))

(defn guard-looped? [mtx guard-coord]
  (loop [guard-coord guard-coord
         bounced-walls #{}
         direction :up]
    (let [next-coord (direction (c/directions guard-coord))
          next-block (get mtx next-coord)
          wall? (#{\O \#} next-block)
          void? (nil? next-block)]
      (cond void? false
            (bounced-walls [direction next-coord]) true
            wall? (recur guard-coord
                         (conj bounced-walls [direction next-coord])
                         (next-direction direction))
            :else (recur next-coord
                         bounced-walls
                         direction)))))

(defn part2 [file]
  (let [text (c/get-input file)
        mtx (c/to-matrix text)
        guard-coord (guard-coord mtx)
        empty-spaces (guard-visited-spots mtx)
        mtx (assoc mtx guard-coord \.)]
    (->> empty-spaces
         (map #(assoc mtx % \O))
         (filter #(guard-looped? % guard-coord))
         (count))))
