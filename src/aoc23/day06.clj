(ns aoc23.day06
  "Wait For It"
  (:require [clojure.string :as str]
            [core :as c]))

(defn- hold-better-than-record [time record-distance hold]
  (let [distance (* hold (- time hold))]
    (when (> distance record-distance)
      {:hold hold
       :distance distance
       :time time
       :record-distance record-distance})))

(defn- keep-holds-better-than-record [{:keys [time record-distance]}]
  (->> (range 0 (inc time))
       (keep #(hold-better-than-record time record-distance %))))

(defn part1 [inp]
  (->> (str/split-lines inp)
       (map #(re-seq #"\d+" %))
       (map (fn [line-nums] (map c/parse-int line-nums)))
       (c/then [[times distances]]
               (map (fn [time distance] {:time time :record-distance distance})
                    times
                    distances))
       (reduce #(* %1 (count (keep-holds-better-than-record %2))) 1)))

(defn- hold-worst-than-record [time record-distance hold]
  (not (hold-better-than-record time record-distance hold)))

(defn- drop-while-worst-than-record [time record holds]
  (->> holds
       (drop-while #(hold-worst-than-record time record %))
       (first)))

(defn- keep-holds-better-than-record-bigint
  "Only faster than keep-holds-better-than-record for big numbers"
  [{:keys [time record-distance]}]
  (let [step 500
        drop-while-hold-worst-than-record #(drop-while-worst-than-record time record-distance %)
        near-first (drop-while-hold-worst-than-record (range 0 (inc time) step))
        near-last (drop-while-hold-worst-than-record (range (inc time) 0 (- step)))
        actual-first (drop-while-hold-worst-than-record (range (max 0 (- near-first step)) time))
        actual-last (drop-while-hold-worst-than-record (range (min time (+ near-last step)) 0 -1))]
    (inc (- actual-last actual-first))))

(defn part2 [inp]
  (->> (str/split-lines inp)
       (map #(str/join "" (re-seq #"\d" %)))
       (map biginteger)
       (c/then [[time distance]]
               {:time time
                :record-distance distance})
       (keep-holds-better-than-record-bigint)))


