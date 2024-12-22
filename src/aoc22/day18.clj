(ns aoc22.day18
  (:require [clojure.string :as s]
            [clojure.core.reducers :as r]
            [clojure.pprint :as pp]
            [clojure.set :as set]))

(def ex-input (c/get-input "input.txt"))
(def input (c/get-input "input.txt"))

(defn parse-input [text]
  (->> (s/split-lines text)
       (mapv (fn [line] (->> (s/split line #",")
                             (mapv #(Integer/parseInt %1)))))
       (into #{})))

(def ex-cubes (parse-input ex-inp))
(def cubes (parse-input inp))

(defn count-sides-touching [cubes]
  (->> (for [cube cubes
             another-cube cubes
             :when (not= cube another-cube)]
         [cube another-cube])
       (r/map (fn [[cube another-cube]]
                (if (= 1 (r/reduce #(+ %1 (abs %2)) 0 (map - cube another-cube)))
                  1 0)))
       (into [])
       (apply +)))

(defn unconnected-sides [cubes]
  (- (* 6 (count cubes)) (count-sides-touching cubes)))


;; Part 1 
(unconnected-sides ex-cubes) ;; 64
(unconnected-sides cubes) ;; 3494

(defn neighbors [[x y z]]
  [[(inc x) y z]
   [(dec x) y z]
   [x (inc y) z]
   [x (dec y) z]
   [x y (inc z)]
   [x y (dec z)]])

(defn flood-fill [cubes]
  (let [mmin (dec (apply min (flatten (vec cubes))))
        mmax (inc (apply max (flatten (vec cubes))))]
    (pp/pprint [mmin mmax])
    (loop [queue [[mmin mmin mmin]]
           flood #{}]
      (if (not-empty queue)
        (let [[hd & tl] queue
              around (remove (set/union cubes flood) (neighbors hd))
              inbounds (filter (fn [point] (every? #(>=  mmax %1  mmin) point)) around)]
          (recur (apply conj tl inbounds) (conj flood hd)))
        flood))))

(defn exterior [cbs]
  (->> (flood-fill cbs)
       (mapcat neighbors)
       (filter cbs)
       (count)))

(exterior ex-cubes) ;; 58
(exterior cubes) ;; 2062

