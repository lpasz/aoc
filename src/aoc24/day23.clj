(ns aoc24.day23
  (:require [core :as c]
            [clojure.string :as s]))

(defn part1 [file] :not-implemented)

(defn part2 [file] :not-implemented)

(def exp (->> (re-seq #"\w+" (c/get-input "input.txt"))
              (partition 2)))

(def sexp (set (map first exp)))

(def mp (->> exp
             (reduce (fn [acc [p1 p2]]
                       (->
                        acc
                        (update p1 (fn [conn] (conj (or conn #{}) p2)))
                        (update p2 (fn [conn] (conj (or conn #{}) p1)))))
                     {})))


(->> (for [i (map first exp)]
       (for [j (mp i)]
         (for [k (mp j)
               :when (and (not= i j)
                          (not= j k)
                          (not= k j))]
           (let [si (mp i)
                 sj (mp j)
                 sk (mp k)]
             (when (and (si j) (si k)
                        (sj i) (sj k)
                        (sk i) (sk j))
               (set [i j k])))

;;
           )))
     (flatten)
     (filter #(= (count %) 3))
     (c/reject nil?)
     (set)
     (filter (partial some #(s/starts-with? % "t")))
     (count)
     ;;
     )

