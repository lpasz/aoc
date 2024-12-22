(ns aoc24.day20
  (:require [core :as c]))

(defn possible-cheats
  ([k]
   (->> (c/cross k 2)
        (map (fn [[p1 _ p2]] [p1 2 p2]))))
  ([k ks]
   (->> ks
        (filter #(not= k %))
        (keep #(let [md (c/euclidean-distance  k %)]
                 (when (<= md 20)
                   [k md %]))))))

(defn valid-cheats [dijk end possible-cheats]
  (->> possible-cheats
       (keep (fn [[c1 md c3]]
               (let [v1 (dijk c1)
                     v3 (dijk c3)]
                 (when (and (number? v1) (number? v3)
                            (> v3 v1))
                   (let [n (if (< (dijk end) v3)
                             (- (dijk end)
                                (+ (- v3 (dijk end)) v1 md))
                             (- v3 v1 md))]
                     (when (pos? n) [#{c1 c3} n]))))))))

(defn apply-cheating [{:keys [end dijk coords]} possible-cheats-fn]
  (->> coords
       (reduce (fn [acc k]
                 (->> (possible-cheats-fn k)
                      (valid-cheats dijk end)
                      (reduce (fn [acc [k v]] (assoc acc k v)) acc)))
               {})
       (group-by second)
       (map (fn [[k v]] [k (count v)]))
       (filter #(>= (first %) 100))
       (map second)
       (c/sum)))

(defn parse-input [file]
  (let [mtx (c/to-matrix (c/get-input file))
        start (c/find-matrix-coord-of mtx \S)
        end (c/find-matrix-coord-of mtx \E)
        graph (c/matrix-to-graph mtx #{\S \E \.})
        dijk (c/dijkstra-shortest graph start)
        coords (keys dijk)]
    {:dijk dijk :coords coords :end end}))

(defn part1 [file]
  (apply-cheating (parse-input file) possible-cheats))

(defn part2 [file]
  (let [input (parse-input file)
        possible-cheats-fn #(possible-cheats % (:coords input))]
    (apply-cheating input possible-cheats-fn)))








