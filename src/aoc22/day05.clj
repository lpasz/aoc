(ns aoc22.day05
  (:require [clojure.string :as s]))

(def ex-inp (c/get-input "input.txt"))
(def inp (c/get-input "input.txt"))

(defn transpose [matrix] (apply map vector matrix))

(defn parse-stacks [stacks]
  (->> (s/split stacks #"\n")
       (map seq)
       (transpose)
       (map reverse)
       (filter #(not= (first %) \space))
       (reduce (fn [acc stack]
                 (assoc acc
                        (Integer/parseInt (str (first stack)))
                        (->> stack
                             rest
                             reverse
                             (filter #(not= % \space)))))
               (sorted-map))))

(defn parse-moves [moves]
  (->> (s/split moves #"[^0-9]")
       (filter not-empty)
       (map #(Integer/parseInt %))
       (partition 3)))

(defn parse-input [[stacks moves]] [(parse-stacks stacks) (parse-moves moves)])

(defn move-n-stacks-from-to-with [stacks [quant from to] mover]
  (-> stacks
      (update from #(drop quant %))
      (update to #(concat (mover quant (stacks from)) %))))

(defn do-calc [mover [stacks moves]]
  (reduce #(move-n-stacks-from-to-with %1 %2 mover) stacks moves))

(defn calc [text mover]
  (->> (s/split text #"\n\n")
       (parse-input)
       (do-calc mover)
       (vals)
       (map first)
       (apply str)))

(defn ex1 [text] (calc text #(reverse (take %1 %2))))
(defn ex2 [text] (calc text take))

(ex1 ex-inp) ;; "CMZ"
(time (ex1 inp)) ;; "PSNRGBTFT"
(ex2 ex-inp) ;; "MCD"
(time (ex2 inp)) ;; "BNTZFPMMW"
