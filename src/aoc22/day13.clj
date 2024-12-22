(ns aoc22.day13
  (:require [clojure.string :as s]))

(def inp (slurp "../inputs/day-13/inp.txt"))
(def ex-inp (slurp "lib/day-13/ex-inp.txt"))

(defn wrap [n] (if (int? n) [n] n))

(defn signal-compare [l1 l2]
  (loop [l1 l1 l2 l2]
    (let [fl1 (first l1)
          fl2 (first l2)
          rl1 (rest l1)
          rl2 (rest l2)]
      (cond
        (= l1 l2) 0
        (and (nil? fl1) (nil? fl2) (empty? rl1) (empty? rl2)) -1
        (and (nil? fl1) (empty? rl1)) -1
        (and (nil? fl2) (empty? fl2)) 1

        (and (int? fl1) (int? fl2)) (cond
                                      (= fl1 fl2) (recur rl1 rl2)
                                      (< fl1 fl2) -1
                                      (> fl1 fl2) 1)

        :else (let [ord (signal-compare (wrap fl1) (wrap fl2))]
                (if (= ord 0)
                  (recur rl1 rl2)
                  ord))))))

(defn parse-lists [text]
  (->> (s/split-lines text)
       (keep #(when (not-empty %) (read-string %)))))

(def right-order -1)

(defn ex1 [text]
  (->>
   (parse-lists text)
   (partition 2)
   (keep-indexed #(when (= right-order (apply signal-compare %2))
                    (inc %1)))
   (apply +)))

(def divider-packets #{[[2]] [[6]]})

(defn ex2 [text]
  (->> (parse-lists text)
       (concat (vec divider-packets))
       (sort signal-compare)
       (keep-indexed #(when (divider-packets %2)
                        (inc %1)))
       (apply *)))

(ex1 ex-inp) ;; 13
(ex1 inp)    ;; 5390
(ex2 ex-inp) ;; 140
(ex2 inp)    ;; 19261
