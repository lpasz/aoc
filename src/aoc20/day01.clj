(ns aoc20.day01
  (:require [clojure.string :as string]
            [core :as c]))

(defn entries-sum-2 [sum entries]
  (loop [result {}
         entries entries]
    (let [entry (first entries)
          entries (rest entries)
          diff (- sum entry)]
      (if (result diff)
        [entry diff]
        (recur (assoc result entry diff) entries)))))

(entries-sum-2 2020 [1721 979 366 299 675 1456])

(def input (map read-string (-> (c/get-input "input.txt")
                                (string/split-lines))))


(apply * (entries-sum-2 2020 input))

(defn entries-sum-3 [sum es]
  (->> (for [e1 es
             e2 es
             e3 es
             :when (= (+ e1 e2 e3) sum)]
         [e1 e2 e3])
       (map sort)
       (distinct)
       (first)))

(entries-sum-3 2020 input)

(apply * (entries-sum-3 2020 input))
