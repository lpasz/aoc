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

(dijk [7 7])
(dijk [5 7])
(dijk end)
(- 78 (- 84 78 18))
(- 78 12)

18
(- (dijk end) (+ (- (dijk [5 7]) (dijk end)) (dijk [7 7]) 2))
(+ 18 6 2)

(- 78 26)
(dijk end)
(into (sorted-map) dijk)
graph

(->>  (keys dijk)
      (reduce (fn [acc k]
                (let [possible-cheats (c/cross k 2)
                      valid-cheats (->> possible-cheats
                                        (keep (fn [[c1 c2 c3]]
                                                (when-not (acc #{c1 c2 c3})
                                                  (let [v1 (dijk c1)
                                                        v2 (mtx c2)
                                                        v3 (dijk c3)]
                                                    (when (and (number? v1) (not (infinite? v1))
                                                               (= \# v2)
                                                               (number? v3) (not (infinite? v3))
                                                               (> v3 v1))
                                                      (let [n (if (< (dijk end) v3)
                                                                (- (dijk end) 
                                                                   (+ (- v3 (dijk end)) v1 2))
                                                                (- v3 v1 2))]
                                                        (when (pos? n) [#{c1 c2 c3} n]))))))))]
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
      )



