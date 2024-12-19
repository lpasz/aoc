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

(def possible-towels
  (memoize (fn [acc pattern towels]
             (cond (= acc pattern) [1]
                   (>= (count acc) (count pattern)) [0]
                   :else
                   (->> towels
                        (map #(str acc %))
                        (filter #(s/starts-with? pattern %))
                        (mapcat #(possible-towels % pattern towels)))))))

(let [[towels wanted-patterns] (s/split (slurp "./assets/day19/input.txt") #"\n\n")
      ts (re-seq #"\w+" towels)
      wp (re-seq #"\w+" wanted-patterns)]
  (->> wp
       (take 2)
       (map (fn [wp] (possible-towels "" wp (filter #(s/index-of wp %) ts))))
       (flatten)
       (c/sum)))
