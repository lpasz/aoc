(ns aoc22.day14
  (:require [clojure.string :as s]))

(def inp (slurp "input.txt"))
(def ex-inp (slurp "input.txt"))

(defn rrange [r1 r2]
  (if (< r1 r2)
    (range r1 (inc r2))
    (range r2 (inc r1))))

(defn rock-edge [[from-x from-y] [to-x to-y]]
  (let [x-range (rrange from-x to-x)
        y-range (rrange from-y to-y)]
    (if (< (count x-range) (count y-range))
      (partition 2 (interleave (repeat (first x-range)) y-range))
      (partition 2 (interleave  x-range (repeat (first y-range)))))))

(defn add-rock-edge [acc [from to]]
  (->> (rock-edge from to)
       (reduce #(assoc %1 (vec %2) :rock) acc)))

(defn to-int [i] (Integer/parseInt i))

(defn parse-line [acc line]
  (->> (s/split line #" -> |,")
       (map to-int)
       (partition 2)
       (partition 2 1)
       (reduce add-rock-edge acc)))

(defn parse-inp [text]
  (->> (s/split-lines text)
       (reduce parse-line {})))

(defn down [[sx sy]] [sx (inc sy)])
(defn left-diagonal [[sx sy]] [(dec sx) (inc sy)])
(defn right-diagonal [[sx sy]] [(inc sx) (inc sy)])

(defn move-sand [acc sand-pos]
  (cond
    (nil? (acc (down sand-pos))) (down sand-pos)
    (nil? (acc (left-diagonal sand-pos))) (left-diagonal sand-pos)
    (nil? (acc (right-diagonal sand-pos))) (right-diagonal sand-pos)))

(defn sand-clogged-entry? [acc entry] (= :sand (acc entry)))
(defn sand-stoped? [sand-pos] (nil? sand-pos))
(defn hit-infinity-floor? [[_x y] infinity-floor] (and infinity-floor (= y infinity-floor)))
(defn free-fall? [[_x y] free-fall] (and free-fall (> y free-fall)))

(defn sand-fall-sim [acc start-sand-pos {:keys [free-fall infinity-floor]}]
  (loop [blockers acc
         curr-sand-pos start-sand-pos]
    (let [next-sand-pos (move-sand blockers curr-sand-pos)]
      (cond
        (sand-clogged-entry? acc start-sand-pos) :stop
        (sand-stoped? next-sand-pos) (assoc blockers curr-sand-pos :sand)
        (free-fall? next-sand-pos free-fall)  :stop
        (hit-infinity-floor? next-sand-pos infinity-floor) (assoc blockers curr-sand-pos :sand)
        :else (recur blockers next-sand-pos)))))

(defn sand-max-capacity [acc quirk]
  (loop [acc acc]
    (let [next-acc (sand-fall-sim acc [500 0] quirk)]
      (if (= next-acc :stop)
        (count (filter #(= %1 :sand) (vals acc)))
        (recur next-acc)))))

(defn ex1 [text]
  (let [rocks (parse-inp text)
        free-fall (apply max (map second (keys rocks)))]
    (sand-max-capacity rocks {:free-fall free-fall})))

(defn ex2 [text]
  (let [rocks (parse-inp text)
        infinity-floor (+ 2 (apply max (map second (keys rocks))))]
    (sand-max-capacity rocks {:infinity-floor infinity-floor})))

(ex1 ex-inp) ;; 24
(ex1 inp) ;; 1003

(ex2 ex-inp) ;; 93 
(ex2 inp) ;; 25771

