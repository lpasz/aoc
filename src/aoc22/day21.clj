(ns aoc22.day21
  (:require [clojure.string :as s]))

(def ex-input (c/get-input "input.txt"))
(def input (c/get-input "input.txt"))

(defn parse [text]
  (->> (s/split-lines text)
       (map (fn [line]
              (s/split line #": | ")))
       (reduce (fn [acc [name & rest]]
                 (if (= (count rest) 1)
                   (assoc acc (keyword name)
                          (Integer/parseInt (first rest)))
                   (assoc acc (keyword name)
                          [(keyword (first rest))
                           (read-string (second rest))
                           (keyword (last rest))])))
               {})))

(def ex-monkey-math-map (parse ex-inp))
(def monkey-math-map (parse inp))

(defn find-recur-monkey-math [value acc]
  (cond (number? value) value
        (= :humn value) 'x
        :else (let [[val1 operation val2] value]
                (list operation
                      (find-recur-monkey-math (val1 acc) acc)
                      (find-recur-monkey-math (val2 acc) acc)))))

(defn half-eval [ll]
  (if (not (seq? ll))
    ll
    (try
      (eval ll)
      (catch Exception _
        (list (first ll)
              (half-eval (second ll))
              (half-eval (nth ll 2)))))))

(defn reverse-apply [ll]
  (if (= 'x ll)
    #(+ 0 %1)
    (let [[op v1 v2] ll
          v1n? (number? v1)
          num (first (filter number? [v1 v2]))
          next (first (filter #(not (number? %)) [v1 v2]))]
      (cond (= op '=) ((reverse-apply next) num)
            (= op '*) #((reverse-apply next) (/ %1 num))
            (= op '+) #((reverse-apply next) (- %1 num))
            (= op '-) #((reverse-apply next) (if v1n?
                                               (+ num (- %1))
                                               (+ %1 num)))
            (= op '/) #((reverse-apply next) (if v1n?
                                               (/ num %1)
                                               (* %1 num)))
            :else #(+ 0 %1)))))

(defn ex2-monkey-update [mps]
  (-> mps
      (update :root (fn [[v1 _ v2]] [v1 '= v2]))
      (assoc :humn :humn)))

(defn ex2 [mps]
  (let [up-map (ex2-monkey-update mps)]
    (->> (find-recur-monkey-math (:root up-map) up-map)
         (half-eval)
         (reverse-apply)
         )))


(defn ex1 [mps]
  (eval (find-recur-monkey-math (:root mps) mps)))


(ex1 ex-monkey-math-map) ;; 152
(ex1 monkey-math-map)    ;; 51928383302238
(ex2 ex-monkey-math-map) ;; 301
(ex2 monkey-math-map)    ;; 3305669217840
