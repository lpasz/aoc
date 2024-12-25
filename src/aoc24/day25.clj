(ns aoc24.day25
  (:require [core :as c]
            [clojure.set :as set]
            [clojure.string :as s]))

(defn max-coord [lks]
  (->> (sort lks)
       (last)))

(defn cap-keys? [lks]
  (let [[x y] (max-coord lks)]
    (every? #(lks [% y]) (range 0 (inc x)))))

(defn cap-locks? [lks]
  (let [[x _] (max-coord lks)]
    (every? #(lks [% 0]) (range 0 (inc x)))))

(defn solid-coords [lks]
  (->> lks
       (filter #(= \# (second %)))
       (keys)
       (set)))

(defn key-lock-fit? [cap-lock cap-key]
  (empty? (set/intersection cap-lock cap-key)))

(defn part1 [file]
  (let [lks (c/get-input file)
        lks (->> (s/split lks #"\n\n")
                 (map c/to-matrix)
                 (map solid-coords))
        cap-keys (filter cap-keys? lks)
        cap-locks (filter cap-locks? lks)]
    (->> (for [ck cap-keys cl cap-locks] (key-lock-fit? cl ck))
         (flatten)
         (filter true?)
         (count))))
