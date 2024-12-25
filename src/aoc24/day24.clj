(ns aoc24.day24
  (:require [core :as c]
            [clojure.string :as s]))

(defn parse-input [file]
  (let [i (c/get-input file)
        [v t] (s/split i #"\n\n")
        v (->> (re-seq #"(\w+?\d+): (\d+)" v)
               (map next)
               (map (c/fnvec keyword parse-long))
               (into {}))
        t (->> (re-seq #"(\w+(\d+)?) (\w+) (\w+(\d+)?) -> (\w+(\d+)?)" t)
               (map next)
               (map #(map keyword (c/reject nil? %))))]
    {:values v :operations t}))

(defn produce-wire-result [file]
  (let [{:keys [values operations]} (parse-input file)]
    (loop [values values operations operations]
      (if (every? number? operations)
        [values operations]
        (let [[values operations] (reduce (fn [[values operations] itm]
                                            (if (number? itm)
                                              [values (conj operations itm)]
                                              (let [[k1 op k2 k3] itm
                                                    v1 (values k1)
                                                    v2 (values k2)
                                                    v3 (when (and (number? v1) (number? v2))
                                                         (case op
                                                           :AND (bit-and v1 v2)
                                                           :XOR (bit-xor v1 v2)
                                                           :OR  (bit-or v1 v2)))]
                                                (if v3
                                                  [(assoc values k3 v3) (conj operations v3)]
                                                  [values (conj operations itm)])))) [values []] operations)]
          (recur values operations))))))

(defn part1 [file]
  (->> (produce-wire-result file)
       (first)
       ((fn [m]
          (let [z-nums (->> (keys m)
                            (map str)
                            (filter #(s/starts-with? % ":z"))
                            (map #(re-seq #"\d+" %))
                            (map first)
                            (sort))]
            (loop [z-nums z-nums r '()]
              (if (empty? z-nums)
                r
                (let [z-num (first z-nums)
                      z-rest (rest z-nums)]
                  (recur z-rest (conj r (m (keyword (str "z" z-num)))))))))))
       (apply str)
       (#(Long/parseLong % 2))))

(defn make-key [letter n]
  (keyword (str letter (format "%02d" n))))

(declare verify-recarry)
(declare verify-carry-bit)
(declare verify-intermediate-xor)

(defn verify-recarry
  "
  Carry its the join of the direct carry bit, and the recarry.

   AND (Recarry) ------------------------------> Carry IN
          ^--------XOR ------------------------> X
                    ^--------------------------> Y

  "
  [wire n formula]
  (c/insp :verify-recarry [wire n])
  (let [[o x y] (formula wire)]
    (if (not= o :AND) false
        (or (and (verify-intermediate-xor x n formula)
                 (verify-carry-bit y n formula))
            (and (verify-intermediate-xor  y n formula)
                 (verify-carry-bit x n formula))))))

(defn verify-direct-carry
  "
  Carry its the join of the direct carry bit, and the recarry.

    OR -----> AND (direct carry)------------------------> X
                      ^ 
                      +---------------------------------> Y

  "
  [wire n formula]
  (c/insp :verify-direct-carry  [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-direct-carry  [o x y])
    (if (not= o :AND) false
        (= (sort [x y]) (map #(make-key % n) ["x" "y"])))))

(defn verify-carry-bit
  "
  Carry its the join of the direct carry bit, and the recarry.

  CARRY OUT BIT ------> OR ---> AND (Recarry) ------------------------------> Carry IN
                        ^              ^--------XOR ----------------+-------> X
                        |                        ^------------------|--+----> Y
                        |                                           |  |
                        +-----> AND (direct carry)------------------+  |
                                     ^---------------------------------+

  "
  [wire n formula]
  (c/insp :verify-carry-bit [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-carry-bit [o x y])
    (cond (and (c/one? n) (not= o :AND)) false
          (c/one? n) (= (sort [x y]) (map #(make-key % (dec n)) ["x" "y"]))
          (not= o :OR) false
          :else (or (and (verify-direct-carry x (dec n) formula)
                         (verify-recarry y (dec n) formula))
                    (and (verify-direct-carry y (dec n) formula)
                         (verify-recarry x (dec n) formula))))))

(defn verify-intermediate-xor
  "
  The intermediate xor consists of the an Z current X and Y
  So for a given Z12

    Z12 -------> XOR ------> INTERMEDIATE-XOR ---> X12
                                     ^-----------> Y12

  We expect the intermediate xor to look like this
  "
  [wire n formula]
  (c/insp :verify-intermediate-xor [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-intermediate-xor [o x y])
    (if (not= o :XOR) false
        (= (sort [x y]) (map #(make-key % n) ["x" "y"])))))

(defn verify-z
  "
  This is the reversed circuit of a Z input

     Z ------> XOR -------> XOR----> X
                |            ^-----> Y
                +------------------> CARRY IN BIT
 "
  [wire n formula]
  (c/insp :verify-z [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-z [o x y])
    (cond (not= o :XOR) false
          (zero? n) (= (sort [x y]) [:x00 :y00])
          :else (or (and (verify-intermediate-xor  x n formula)
                         (verify-carry-bit y n formula))
                    (and (verify-intermediate-xor y n formula)
                         (verify-carry-bit x n formula))))))

(defn attempt-to-run-example
  "
 this is what runs part 2
 part two was mostly copied from this video https://www.youtube.com/watch?v=SU6lp6wyd3I&t=2716s
 but we basically reverting an addition with carry over bit, and checking if that is the actual operations happening.
 If it fails, we can go back and check the input to find the error and fix it.
 If you fix it manually if should be able to get to the next step.
 Once you get to z45 you are at the very end of the input.

 Here is the expected sum, with carry over bit this is the original circuit, the others are reversed, because that is what
 we are essencially doing. going back from Z to determine if the rest is correct.
 
 
        X -------+----> XOR----+-------> XOR ------------------> SUM (Z)
        Y ----+--|-------^     |         ^
 CARRY IN ----|--|-------------|---------+---AND-----> OR------> CARRY OUT
              |  |             +--------------^        ^
              |  +-------------------------------AND---+
              +-----------------------------------^


  
  "
  [file]
  (let [{:keys [operations]} (parse-input file)
        formulas (->> operations
                      (map (fn [[x o y z]] [z [o x y]]))
                      (into (sorted-map)))]
    (loop [n (range 0 46)]
      (if (verify-z (make-key "z" (first n)) (first n) formulas)
        (recur (rest n))
        (first n)))))

;; This are the changes made, they were made in the input.txt, for comparing, we kept and original.txt
;; z16 <-> hmk
;; fhp <-> z20
;; tpc <-> rvf
;; z33 <-> fcd
(->> ["z16" "hmk"
      "fhp" "z20"
      "tpc" "rvf"
      "z33" "fcd"]
     (sort)
     (s/join ","))

