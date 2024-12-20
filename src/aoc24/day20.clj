(ns aoc24.day20
  (:require [core :as c]))

(defn part1 [file] :not-implemented)

(defn part2 [file] :not-implemented)

(def exp  "./assets/day20/example.txt")
(def exp2  "./assets/day20/example2.txt")
(def inp  "./assets/day20/input.txt")

(def mtx (c/to-matrix (slurp inp)))
(def start (c/find-matrix-coord-of mtx \S))
(def end (c/find-matrix-coord-of mtx \E))
(def graph (c/matrix-to-graph mtx #{\S \E \.}))
(def dijk (c/dijkstra-shortest graph start))

(let [ks (keys dijk)]
  (->>  ks
        (reduce (fn [acc k]
                  (let [possible-cheats (->> ks
                                             (filter #(not= k %))
                                             (keep (fn [k1]
                                                     (let [md (c/manhattan-distance k k1)]
                                                       (when (<= md 20)
                                                         [k md k1])))))
                        valid-cheats (->> possible-cheats
                                          (keep (fn [[c1 md c3]]
                                                  (let [v1 (dijk c1)
                                                        v3 (dijk c3)]
                                                    (when (and (number? v1) (not (infinite? v1))
                                                               (number? v3) (not (infinite? v3))
                                                               (> v3 v1))
                                                      (let [n (if (< (dijk end) v3)
                                                                (- (dijk end)
                                                                   (+ (- v3 (dijk end)) v1 md))
                                                                (- v3 v1 md))]
                                                        (when (pos? n) [#{c1 c3} n])))))))]
                    (reduce (fn [acc [k v]]
                              (assoc acc k v))
                            acc
                            valid-cheats)))
                {})
        (group-by second)
        (map (fn [[k v]] [k (count v)]))
       ;; (into (sorted-map))
        (filter #(>= (first %) 100))
        (map second)
        (c/sum)
     ;; 
        ))
;; is too low
;; you need the tax cab distance here or manhathan distance between 2 points.








