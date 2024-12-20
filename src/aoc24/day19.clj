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
         ;; if we reuse the part2 solution
         ;; (map #(count-towel-combinations % (in-pattern % towels)))
         ;; (c/reject zero?)
         (count))))

(def count-towel-combinations
  ;; it took a long time for me to get this right, basically because the return value of the memoize was a list of things
  ;; make the return value simple so you can trully benefit from memoize
  (memoize (fn [w ts]
             (if (empty? w) 1
                 (->> ts
                      (filter #(s/starts-with? w %))
                      (map count)
                      ;; if we keep the one below if fails because of heap space,
                      ;; (mapcat #(count-towel-combinations (subs w %) ts))))))) ;; fails spetacular if we return this
                      ;; so we need the reduce to make it a simple number
                      (reduce #(+ %1 (count-towel-combinations (subs w %2) ts)) 0))))))

(defn in-pattern [pattern towels]
  (filter #(s/index-of pattern %) towels))

(defn part2 [file]
  (let [[towels wanted-patterns] (s/split (slurp file) #"\n\n")
        towels (set (re-seq #"\w+" towels))
        patterns (re-seq #"\w+" wanted-patterns)]
    (->> patterns
         (map #(count-towel-combinations % (in-pattern % towels)))
         (c/sum))))




