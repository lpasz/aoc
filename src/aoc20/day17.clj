(ns aoc20.day17
  (:require [clojure.string :as s]
            [core :as c]))

(def input (c/get-input "input.txt"))
(def ex1 (c/get-input "./src/advent-of-code-2020/day-17/ex1.txt"))

(defn distance [x]
  (range (dec x) (+ 2 x)))


(defn neighbors-coord [coord]
  (->> coord
       (map distance)
       (c/cartesian-prod)
       (filter #(not= % coord))))

(defn pocket-dimention [text n-dimentions]
  (->> (s/split-lines text)
       (keep-indexed (fn [y line]
                       (->> line
                            (keep-indexed (fn [x itm] (when (= \# itm) (concat [x y] (repeat (- n-dimentions 2) 0))))))))
       (flatten)
       (partition n-dimentions)
       (set)))

(defn count-atives [coord dimention]
  (->> coord
       (neighbors-coord)
       (filter dimention)
       (count)))

(defn will-remain-active? [coord dimention]
  (boolean (#{2 3} (count-atives coord dimention))))

(defn will-activate? [coord dimention]
  (boolean (#{3} (count-atives coord dimention))))

(defn active? [coord dimention] (dimention coord))

(defn will-be-active-coordinate [coord dimention]
  (if (active? coord dimention)
    (when (will-remain-active? coord dimention) coord)
    (when (will-activate? coord dimention) coord)))

(defn new-dimention [dimention]
  (->> dimention
       (mapcat neighbors-coord)
       (into #{})))

(defn do-cycle [dimention]
  (->> dimention
       (new-dimention)
       (keep #(will-be-active-coordinate % dimention))
       (set)))

(defn after-n-cycles [n dimention]
  (reduce (fn [dimention _] (do-cycle dimention)) dimention (range n)))

(defn active-after-n-cycles [n text n-dim]
  (->> (pocket-dimention text n-dim)
       (after-n-cycles n)
       (filter second)
       (count)))

;; part 1
(active-after-n-cycles 6 ex1 3) ;; 112
(active-after-n-cycles 6 input 3) ;; 218

;; part 2
(active-after-n-cycles 6 ex1 4) ;; 848
(active-after-n-cycles 6 input 4) ;; 1908
