(ns aoc24.day17
  (:require
   [clojure.string :as s]
   [core :as c]))

(defn operand [operand [r1 r2 r3]]
  (get {4 r1 5 r2 6 r3} operand operand))

(defn apply-ins [ip [r1 r2 r3 :as regs] ins out]
  (if (>= (inc ip) (count ins))
    out
    (let [opcode (get ins ip)
          literal (get ins (inc ip))
          combo (operand literal regs)
          nip (+ 2 ip)]
      (case opcode
        0 (recur nip [(bit-shift-right r1 combo) r2 r3] ins out)
        1 (recur nip [r1 (bit-xor r2 literal) r3] ins out)
        2 (recur nip [r1 (mod combo 8) r3] ins out)
        3 (if (zero? r1) (recur nip regs ins out) (recur literal regs ins out))
        4 (recur nip [r1 (bit-xor r2 r3) r3] ins out)
        5 (recur nip regs ins (conj out (mod combo 8)))
        6 (recur nip [r1 (bit-shift-right r1 combo) r3] ins out)
        7 (recur nip [r1 r2 (bit-shift-right r1 combo)] ins out)))))

(defn parse-input [file]
  (let [[regs ins] (s/split (c/get-input file) #"\n\n")
        regs (c/extract-numbers regs)
        ins (into [] (c/extract-numbers ins))]
    [regs ins]))

(defn part1 [file]
  (let [[regs ins] (parse-input file)]
    (apply-ins 0 regs ins [])))

;; this is the same as before, but only for my input.
(defn my-input-machine
  "Example of a specific solution for the machine given in input.txt"
  [a ins out]
  (let [b (mod a 8) ;; 2, 4
        b (bit-xor b 5) ;; 1 5
        c (bit-shift-right a b) ;; 7 5
        b (bit-xor b 6) ;; 1 6
        a (bit-shift-right a 3) ;; 0 3
        b (bit-xor b c) ;; 4 2
        out (conj out (mod b 8)) ;; 5 5
        ]
    (if (zero? a)
      out
      (recur a (rest ins) out))))

(defn reverse-machine
  "ins is our instruction list, and ans the answer up to that point"
  [ins ans]
  (if (empty? ins) [ans]
      (->> (range 8)
           (mapcat (fn [t]
                    ;; we need to work our a back from the start of previous example
                    ;; round one we have a an A that results in a b % 8 == 0
                    ;; in the next recur we need to do recreate our previous A as well
                    ;; this is what the first line is doing.
                    ;; We recreate A and check if that A would produce a B % 8 == expected answer
                     (let [;; This solution assumes that A is always an octane
                           a (bit-shift-left ans 3) ;; reverse the operation 0 3 to get A at the very start of the cycle
                           a (+ t a) ;; this create the possible values it can have  
                           b (mod a 8) ;; 2, 4
                           b (bit-xor b 5) ;; 1 5
                           c (bit-shift-right a b) ;; 7 5
                           b (bit-xor b 6) ;; 1 6
                           b (bit-xor b c) ;; 4 2
                           out (mod b 8) ;; 5 5
                           ]
                       (when (= out (peek ins))
                         (reverse-machine (pop ins) a))))))))

(defn part2 []
  ;; it only works for this input
  (-> (parse-input "./assets/aoc24/day17/input.txt")
      (second)
      (reverse-machine 0)
      (sort)
      (first)))

