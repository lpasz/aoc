(ns aoc22.day07
  (:require [clojure.string :as s]))

(defn parse [line]
  (cond (s/starts-with? line "cd") (s/replace line #"(cd.|\n)" "")
        (s/starts-with? line "ls") (reduce #(if (not-empty %2)
                                              (+ %1 (Integer/parseInt %2))
                                              %1)
                                           0
                                           (s/split line #"[^0-9]"))))

(defn cmds [text]
  (->> (s/split text #"\$ ")
       (keep not-empty)
       (map parse)))

(defn add-to-folders [fs cd cmd]
  (loop [cd cd fs fs]
    (if (not-empty cd)
      (recur (drop-last cd)
             (update fs (s/join "/" cd) #(if (nil? %)
                                           cmd
                                           (+ cmd %))))
      fs)))

(defn fs [text]
  (->> (cmds text)
       (reduce (fn [[cd fs] cmd]
                 (cond
                   (number? cmd) [cd (add-to-folders fs cd cmd)]
                   (= ".." cmd)  [(into [] (drop-last cd)) fs]
                   (string? cmd) [(conj cd cmd) fs]))
               [[] {}])
       (second)))

(defn part1 [text]
  (->> (fs text)
       (vals)
       (filter number?)
       (filter #(>= 100000 %))
       (apply +)))

(def total-space 70000000)
(def space-req 30000000)

(defn part2 [text]
  (let [fs (fs text)
        used-space (fs "/")
        remaining-space (- total-space used-space)
        need-delete-at-least (- space-req remaining-space)]
    (->> fs
         (vals)
         (filter #(> % need-delete-at-least))
         (sort)
         (first))))



