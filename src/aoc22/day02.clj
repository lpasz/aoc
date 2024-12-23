(ns aoc22.day02
  (:use [clojure.set :only [map-invert]])
  (:require [clojure.string :as s]))

(def ex1-encrypt {\A :rock \B :paper \C :scissor \X :rock \Y :paper \Z :scissor})
(def ex2-encrypt {\X :lose \Y :tie \Z :win})
(def win {:paper :rock :rock :scissor :scissor :paper})
(def lose (map-invert win))
(def points {:rock 1 :paper 2 :scissor 3 :win 6 :lose 0 :tie 3})

(defn game-result [you adv]
  (cond (= (-> you win) adv)  :win
        (= adv you) :tie
        :else :lose))

(defn get-result-points [[adv you]] (+ (points you) (points (game-result you adv))))

(defn part1 [text]
  (->> (s/split-lines text)
       (reduce (fn [acc line] (->> (seq line)
                                   ((juxt #(ex1-encrypt (first %))
                                          #(ex1-encrypt (nth % 2))))
                                   get-result-points
                                   (+ acc))) 0)))

(defn my-play [[adv result]]
  (cond (= :tie result) [adv adv]
        (= :win result) [adv (lose adv)]
        (= :lose result) [adv (win adv)]))

(defn part2 [text]
  (->> (s/split-lines text)
       (reduce (fn [acc line] (->> (seq line)
                                   ((juxt #(ex1-encrypt (first %))
                                          #(ex2-encrypt (nth % 2))))
                                   my-play
                                   get-result-points
                                   (+ acc))) 0)))

