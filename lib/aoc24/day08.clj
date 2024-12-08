(ns aoc24.day08
  (:require [core :as c]))

(defn antinodes [p1 p2]
  (let [[x1 _y1] p1
        [x2 _y2] p2
        {:keys [slope]} (c/compute-line p1 p2)
        dx  (abs (- x1 x2))
        dx1 (+ (max x1 x2) dx)
        dx2 (- (min x1 x2) dx)]
    [(c/compute-point-for-x p1 slope dx1)
     (c/compute-point-for-x p1 slope dx2)]))

(defn part1 [file]
  (let [mtx (c/to-matrix (slurp file))
        antenas (->> mtx
                     (filter #(not= \. (second %)))
                     (group-by second)
                     (map (fn [[k v]] [k (map first v)]))
                     (into {}))]
    (->> (vals antenas)
         (mapcat #(c/unique-combinations % %))
         (mapcat (fn [[p1 p2]] (antinodes p1 p2)))
         (filter #(contains? mtx %))
         (set)
         (count))))

(defn antinodes-t [p1 p2 mtx]
  (let [[x1 _y1] p1
        [x2 _y2] p2
        {:keys [slope]} (c/compute-line p1 p2)
        dx  (abs (- x1 x2))
        dx1 #(+ (max x1 x2) (* % dx))
        dx2 #(- (min x1 x2) (* % dx))]
    (map (fn [dx]
           (->> (iterate inc 1)
                (map dx)
                (map #(c/compute-point-for-x p1 slope %))
                (take-while #(contains? mtx %)))) [dx1 dx2])))

(defn part2 [file]
  (let [mtx (c/to-matrix (slurp file))
        all-antenas (->> mtx (filter #(not= \. (second %))))
        antenas (->> all-antenas
                     (group-by second)
                     (map (fn [[k v]] [k (map first v)]))
                     (into {}))]
    (->> (vals antenas)
         (mapcat #(c/unique-combinations % %))
         (mapcat (fn [[p1 p2]] (antinodes-t p1 p2 mtx)))
         (mapcat identity)
         (concat (map first all-antenas))
         (set)
         (count))))



