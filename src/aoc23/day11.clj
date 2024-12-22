(ns aoc23.day11
  "Cosmic Expansion"
  (:require [core :as c]
            [clojure.string :as str]))

(defn- parse-input [inp]
  (->> (str/split-lines inp)
       (map seq)))

(defn- all-space? [line]
  (every? #(= \. (second %)) line))

(defn- euclidean-distance [[x1 y1] [x2 y2]]
  (+ (abs (- x2 x1)) (abs (- y2 y1))))

(defn- distance-from-galaxy-to-all-others [coords]
  (->> coords
       (reduce (fn [acc coord]
                 (reduce #(if (and (not= coord %2) (not (contains? %1 #{coord %2})))
                            (assoc %1 #{coord %2} (euclidean-distance coord %2))
                            %1)
                         acc
                         coords))
               {})))

(defn- to-mtx-with-coords [inp]
  (map-indexed (fn [idy line] (map-indexed (fn [idx itm] [[idx idy] itm]) line)) inp))

(defn- remap-coord [x jmp n]
  (+ (* jmp (dec n)) x))

(defn- remap-coords [jmp n [[x y] itm] axis]
  (axis {:y [[x (remap-coord y jmp n)] itm]
         :x [[(remap-coord x jmp n) y] itm]}))

(defn- expand [space-expansion-coef axis mtx]
  (->> mtx
       (reduce (fn [[jmp acc] line]
                 (if (all-space? line)
                   [(inc jmp) (conj acc line)]
                   [jmp (conj acc (map #(remap-coords jmp space-expansion-coef % axis) line))]))
               [0 []])
       (second)))

(defn- expand-space [space-expansion-coef mtx]
  (->> mtx
       (expand space-expansion-coef :y)
       (c/transpose)
       (expand space-expansion-coef :x)
       (c/transpose)
       (c/flatten-once)))

(defn- sum-galaxy-distances [input space-expansion-coef]
  (->> (parse-input input)
       (to-mtx-with-coords)
       (expand-space space-expansion-coef)
       (keep (fn [[coord itm]] (when (= \# itm) coord)))
       (distance-from-galaxy-to-all-others)
       (vals)
       (c/sum)))

(defn part1 [input]
  (sum-galaxy-distances input 2))

(defn part2 [input]
  (sum-galaxy-distances input 1000000))
