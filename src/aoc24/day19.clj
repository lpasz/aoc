(ns aoc24.day19
  (:require [core :as c]
            [clojure.string :as s]))

(defn possible-towel? [acc pattern towels]
  (let [f (first acc)]
    (cond (= f pattern) true
          (empty? acc) false
          :else (recur (->> towels
                            (map #(str f %))
                            (filter #(s/starts-with? pattern %))
                            (reduce conj (rest acc)))
                       pattern
                       towels))))

(defn part1 [file]
  (let [[towels wanted-patterns] (s/split (slurp file) #"\n\n")
        towels (re-seq #"\w+" towels)
        wanted-patterns (re-seq #"\w+" wanted-patterns)]
    (->> wanted-patterns
         (map #(possible-towel? [""] % towels))
         (filter true?)
         (count))))

(def cnt-variants
  ;; simplify the return value
  (memoize (fn [w ts]
             (if (empty? w) [1]
                 (->> ts
                      (filter #(s/starts-with? w %))
                      (mapcat #(cnt-variants (subs w (count %)) ts))
                      (c/sum)
                      (list))))))

(let [[towels wanted-patterns] (s/split (slurp "./assets/day19/input.txt") #"\n\n")
      ts (set (re-seq #"\w+" towels))
      wp (re-seq #"\w+" wanted-patterns)]
  (->> wp
       (map (fn [w] [w (filter #(s/index-of w %) ts)]))
       (mapcat #(cnt-variants (first %) (second %)))
       (c/sum)))
