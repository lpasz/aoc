(ns aoc23.day13
  "Point of Incidence"
  (:require [core :as c]
            [clojure.string :as str]))

(def example (c/get-input "example.txt"))
(def input (c/get-input "input.txt"))

(defn- reflection? [idx-mtx idx1 line1 idx2 line2]
  (cond
    (or (nil? line1) (nil? line2)) true
    (not= line1 line2) false
    (= line1 line2) (recur idx-mtx
                           (dec idx1) (get idx-mtx (dec idx1))
                           (inc idx2) (get idx-mtx (inc idx2)))))

(defn- one-diff? [line1 line2]
  (->> (map = line1 line2)
       (c/reject true?)
       (count)
       (c/one?)))

(defn- reflection-diff?
  ([idx-mtx idx1 line1 idx2 line2] (reflection-diff? idx-mtx idx1 line1 idx2 line2 0))
  ([idx-mtx idx1 line1 idx2 line2 acc-diff]
   (cond (or (nil? line1) (nil? line2)) (c/one? acc-diff)

         (and (zero? acc-diff)
              (one-diff? line1 line2))  (recur idx-mtx
                                               (dec idx1) (get idx-mtx (dec idx1))
                                               (inc idx2) (get idx-mtx (inc idx2))
                                               1)

         (= line1 line2)                (recur idx-mtx
                                               (dec idx1) (get idx-mtx (dec idx1))
                                               (inc idx2) (get idx-mtx (inc idx2))
                                               acc-diff))))


(defn- reflections-lines [mtx reflect?]
  (let [idx-mtx (into (sorted-map) (map-indexed (fn [idx line] [(inc idx) line]) mtx))]
    (->> (partition 2 1 idx-mtx)
         (filter (fn [[[idx1 line1] [idx2 line2]]] (reflect? idx-mtx idx1 line1 idx2 line2)))
         (first)
         (ffirst))))

(defn- reflections-columns [mtx reflection?]
  (reflections-lines (c/transpose mtx) reflection?))

(defn- find-reflections [mtx reflection?]
  {:lines (reflections-lines mtx reflection?)
   :columns (reflections-columns mtx reflection?)})

(defn- calc-reflections [{:keys [lines columns]}]
  (cond (nil? lines) columns
        (nil? columns) (* 100 lines)))

(defn- part [input pred]
  (->> (str/split input #"\n\n")
       (map #(map seq (str/split-lines %)))
       (map #(find-reflections % pred))
       (map calc-reflections)
       (c/sum)))

(defn part1 [inp] (part input reflection?))
(defn part2 [inp] (part input reflection-diff?))

(comment
  (assert (= 405 (part1 example)))
  (assert (= 29846 (part1 input)))
  (assert (= 400 (part2 example)))
  (assert (= 25401 (part2 input)))
  ;;
  )
