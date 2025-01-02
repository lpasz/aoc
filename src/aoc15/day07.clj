(ns aoc15.day07
  (:require
   [core :as c]))

(defn parse-input [file]
  (->> (re-seq #"(.*) -> (.*)" (c/get-input file))
       (map rest)
       (map (fn [[input output]]
              (let [wire-value (re-matches #"\d+" input)
                    wire-value-i (re-matches #"\w+" input)
                    wire-and (re-matches #"(\w+) AND (\w+)" input)
                    wire-and-i (re-matches #"(\d+) AND (\w+)" input)
                    wire-or (re-matches #"(\w+) OR (\w+)" input)
                    wire-lshift (re-matches #"(\w+) LSHIFT (\d+)" input)
                    wire-rshift (re-matches #"(\w+) RSHIFT (\d+)" input)
                    wire-not (re-matches #"NOT (\w+)" input)]
                (cond
                  wire-value [:VALUE (parse-long input) output]
                  wire-value-i [:VALUEI input output]
                  wire-and-i [:ANDI (parse-long (second wire-and-i)) (c/third wire-and-i) output]
                  wire-and [:AND (second wire-and) (c/third wire-and) output]
                  wire-or [:OR (second wire-or) (c/third wire-or) output]
                  wire-rshift [:RSHIFT (second wire-rshift) (parse-long (c/third wire-rshift)) output]
                  wire-lshift [:LSHIFT (second wire-lshift) (parse-long (c/third wire-lshift)) output]
                  wire-not  [:NOT (second wire-not) output]))))))

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

; x AND y -> d
; x OR y -> e
; x LSHIFT 2 -> f
; y RSHIFT 2 -> g
; NOT x -> h
; NOT y -> i

