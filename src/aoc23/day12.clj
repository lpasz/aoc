(ns aoc23.day12
  "Hot Springs"
  (:require [core :as c]
            [clojure.string :as str]))

(defn- parse-input [inp]
  (->> (str/split-lines inp)
       (map #(str/split % #" "))
       (map (fn [[spring-data rec-data]]
              [spring-data (->> rec-data
                                (re-seq #"\d+")
                                (map c/parse-int))]))))

(defn part2-fy [[text recovery] n]
  [(str/join "?" (repeat n text))
   (flatten (repeat n recovery))])



(def dfs-spring-pattern
  (memoize
   (fn [springs pattern curr-cnt]
     (cond
       (and (empty? springs)
            (empty? pattern)
            (zero? curr-cnt))             1

       (and (empty? springs)
            (= [curr-cnt] pattern))       1

       (= \? (first springs))             (+ (dfs-spring-pattern (cons \# (rest springs)) pattern curr-cnt)
                                             (dfs-spring-pattern (cons \. (rest springs)) pattern curr-cnt))

       (= \# (first springs))             (dfs-spring-pattern (rest springs) pattern (inc curr-cnt))

       (and (= \. (first springs))
            (zero? curr-cnt))             (dfs-spring-pattern (rest springs) pattern 0)

       (and (= \. (first springs))
            (= (first pattern) curr-cnt)) (dfs-spring-pattern (rest springs) (rest pattern) 0)

       :else                              0))))

(defn part1 [inp]
  (->> (parse-input inp)
       (pmap #(dfs-spring-pattern (first %) (second %) 0))
       (c/sum)))

(defn part2 [inp]
  (->> (parse-input inp)
       (pmap #(part2-fy % 5))
       (pmap #(dfs-spring-pattern (first %) (second %) 0))
       (c/sum)))

