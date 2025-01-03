(ns aoc23.day02
  "Cube Conundrum"
  (:require [clojure.string :as str]
            [core :as c]))

(def can-possibly-contain-cubes {:red 12 :green 13 :blue 14})

(defn- to-cube-color-and-count [cube-count-and-color]
  (let [[count color] (-> cube-count-and-color
                          (str/trim)
                          (str/split #"\s"))]
    [(keyword color) (c/parse-int count)]))

(defn- to-cubes-taken-out-of-bag [cubes-taken-out-of-bag]
  (->> (str/split cubes-taken-out-of-bag #",")
       (map to-cube-color-and-count)
       (into {})))

(defn- to-taken-out-of-bag-in-turns [turns]
  (->> (str/split turns  #";")
       (map to-cubes-taken-out-of-bag)))

(defn- to-id-and-bag-turns [line]
  (let [[[_line id turns]] (re-seq #"Game\s(\d+):\s(.*)" line)]
    [(c/parse-int id) (to-taken-out-of-bag-in-turns turns)]))

(defn- possibly-contained-in-round? [round]
  (every? (fn [[color count]]
            (<= (get round color 0) count))
          can-possibly-contain-cubes))

(defn- possibly-contained-in-rounds? [[_id rounds]]
  (every? possibly-contained-in-round? rounds))

(defn- power-of-cubes [[_id turns]]
  (let [color-max-cubes (fn [color] (c/max-by #(get % color 0) turns))]
    (->> (keys can-possibly-contain-cubes)
         (map color-max-cubes)
         (apply *))))

(defn part1 [inp]
  (->> (str/split-lines inp)
       (map to-id-and-bag-turns)
       (filter possibly-contained-in-rounds?)
       (map first)
       (apply +)))

(defn part2 [inp]
  (->> (str/split-lines inp)
       (map to-id-and-bag-turns)
       (map power-of-cubes)
       (apply +)))




