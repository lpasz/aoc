(ns aoc22.day11
  (:require [clojure.string :as s]))

(def inp (slurp "input.txt"))
(def ex-inp (slurp "input.txt"))

(defn to-int [i] (Integer/parseInt i))

(defn parse-starting-items [starting-items]
  (->> (s/split (last (s/split starting-items #": ")) #", ")
       (map to-int)
       (into [])))

(defn parse-operation [operation]
  (-> (str "(fn [old] ("
           (s/replace operation #"Operation: new = old " "")
           " old ))")
      (read-string)
      (eval)))

(defn parse-test [test]
  (let [tests (-> (apply str test) (s/replace #"[^0-9]+" ",") (s/split #","))
        [divisible-for m1 m2] (->> tests (drop 1) (map to-int))]
    {:test #(if (zero? (rem % divisible-for)) m1 m2)
     :divisible-for divisible-for}))

(defn parse-line [line]
  (let [[_ starting-items operation & test] (s/split-lines line)]
    (merge {:items (parse-starting-items starting-items)
            :operation (parse-operation operation)
            :inspected 0}
           (parse-test test))))

(defn parse-inp [text]
  (->> (s/split text #"\n\n")
       (map parse-line)
       (into [])))

(defn throw-item-to-other-monkeys [monkeys item monkey-num relief denominator]
  (let [monkey (get monkeys monkey-num)
        worry-lvl ((:operation monkey) item)
        worry-lvl (quot worry-lvl relief)
        worry-lvl (rem worry-lvl denominator)
        trow-to ((:test monkey) worry-lvl)]
    (update-in monkeys [trow-to :items] conj worry-lvl)))

(defn throw-all-item-to-other-monkeys [monkeys items monkey-num relief denominator]
  (->> items
       (reduce #(throw-item-to-other-monkeys %1 %2 monkey-num relief denominator) monkeys)))

(defn do-monkey-turn [monkeys monkey-num relief denominator]
  (let [items (get-in monkeys [monkey-num :items])]
    (-> monkeys
        (throw-all-item-to-other-monkeys items monkey-num relief denominator)
        (update-in [monkey-num :items] (constantly []))
        (update-in [monkey-num :inspected] + (count items)))))

(defn do-turn [monkeys _curr_turn relief denominator]
  (->> (count monkeys)
       (range)
       (reduce #(do-monkey-turn %1 %2 relief denominator) monkeys)))

(defn do-turns [text turns relief]
  (let [monkeys (parse-inp text)
        denominator (->> monkeys (map :divisible-for) (reduce *))]
    (->> (range turns)
         (reduce #(do-turn %1 %2 relief denominator) monkeys)
         (map :inspected)
         (sort >)
         (take 2)
         (apply *))))

(defn ex1 [text] (do-turns text 20 3))
(defn ex2 [text] (do-turns text 10000 1))

(ex1 ex-inp) ;; 10605
(ex2 ex-inp) ;; 2713310158
(ex1 inp)    ;; 55458
(ex2 inp)    ;; 14508081294
