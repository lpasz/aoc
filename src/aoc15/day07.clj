(ns aoc15.day07
  (:require [core :as c]))

(defn parse-input [file]
  (->> (re-seq #"(.*) -> (.*)" (c/get-input file))
       (map rest)
       (map (fn [[input output]]
              (c/cond-fn
               (re-matches #"\d+" input) (fn [_] [:VALUE (parse-long input) output])
               (re-matches #"\w+" input) (fn [_] [:VALUEI input output])
               (re-matches #"(\d+) AND (\w+)" input) (fn [wire-and-i] [:ANDI (parse-long (second wire-and-i)) (c/third wire-and-i) output])
               (re-matches #"(\w+) AND (\w+)" input) (fn [wire-and] [:AND (second wire-and) (c/third wire-and) output])
               (re-matches #"(\w+) OR (\w+)" input) (fn [wire-or] [:OR (second wire-or) (c/third wire-or) output])
               (re-matches #"(\w+) RSHIFT (\d+)" input) (fn [wire-rshift] [:RSHIFT (second wire-rshift) (parse-long (c/third wire-rshift)) output])
               (re-matches #"(\w+) LSHIFT (\d+)" input) (fn [wire-lshift] [:LSHIFT (second wire-lshift) (parse-long (c/third wire-lshift)) output])
               (re-matches #"NOT (\w+)" input)  (fn [wire-not] [:NOT (second wire-not) output]))))))

(defn part1 [file]
  (loop [acc (sorted-map)
         values (parse-input file)]
    (let [acc1 (reduce (fn [acc [op v1 v2 v3]]
                         (case op
                           :VALUE (assoc acc v2 v1)
                           :VALUEI (if (contains? acc v1)
                                     (assoc acc v2 (acc v1))
                                     acc)
                           :AND (if (and (contains? acc v1) (contains? acc v2))
                                  (assoc acc v3 (bit-and (acc v1) (acc v2)))
                                  acc)
                           :ANDI (if (contains? acc v2)
                                   (assoc acc v3 (bit-and v1 (acc v2)))
                                   acc)
                           :OR (if (and (contains? acc v1) (contains? acc v2))
                                 (assoc acc v3 (bit-or (acc v1) (acc v2)))
                                 acc)
                           :RSHIFT (if (contains? acc v1)
                                     (assoc acc v3 (bit-shift-right (acc v1) v2))
                                     acc)
                           :LSHIFT (if (contains? acc v1)
                                     (assoc acc v3 (bit-shift-left (acc v1) v2))
                                     acc)
                           :NOT (if (contains? acc v1)
                                  (assoc acc v2 (bit-and (bit-not (acc v1)) 16rFFFF))
                                  acc)))
                       acc
                       values)]
      (if (= acc acc1)
        (acc "a")
        (recur acc1 values)))))

(def part2 part1)
