(ns aoc22.day10
  (:require [clojure.string :as s]))

(def input (c/get-input "input.txt"))
(def ex-input (c/get-input "input.txt"))

(defn to-instruction [line]
  (if (= "noop" line)
    [:noop]
    [:noop
     (->> (s/split line #" ")
          (last)
          (Integer/parseInt))]))

(defn parse-input [text]
  (->> (s/split-lines text)
       (mapcat to-instruction)
       (reductions (fn [[cycle reg] instruction]
                     (if (= :noop instruction)
                       [(inc cycle) reg]
                       [(inc cycle) (+ instruction reg)]))
                   [1 1])
       (into (sorted-map))))

(def cycles [20 60 100 140 180 220])

(defn ex1 [text]
  (let [ci (parse-input text)]
    (reduce #(+ %1 (* %2 (ci %2))) 0 cycles)))

(defn bit-on? [cycle reg]
  (#{reg (+ 1 reg) (+ 2 reg)} (rem cycle 40)))

(defn p [text] (do (print text) text))

(defn ex2 [text]
  (->> (parse-input text)
       (map (fn [[cycle reg]] (if (bit-on? cycle reg) \# \space)))
       (partition 40)
       (map #(apply str %))
       (s/join "\n")
       (p)))

(ex1 ex-inp) ;; 13140
(ex1 inp)    ;; 13440
(ex2 ex-inp) ;; ~nothing
(ex2 inp)    ;; PBZGRAZA
