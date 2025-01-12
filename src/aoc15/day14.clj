(ns aoc15.day14
  (:require [core :as c]))

(defn parse-input [file]
  (->> (c/get-input file)
       (re-seq #"(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.")
       (map rest)
       (map (c/fnvec identity parse-long parse-long parse-long))))

(defn simulate-run [seconds [_reindeer speed runtime resttime]]
  (let [full-cycles (quot seconds (+ runtime resttime))
        full-cycle-distance (* full-cycles (* speed runtime))
        remaining-seconds (rem seconds (+ runtime resttime))]
    (loop [seconds remaining-seconds
           runtime runtime
           total-distance full-cycle-distance]
      (cond (zero? seconds) total-distance
            (zero? runtime) total-distance
            :else (recur (dec seconds) (dec runtime) (+ speed total-distance))))))

(defn part1 [file]
  (->> (parse-input file)
       (map (partial simulate-run 2503))
       (apply max)))

(defn part2
  ([file]
   (part2 file 2503))
  ([file seconds]
   (let [reindeers (parse-input file)]
     (->> (c/inc-range 1 seconds)
          (mapcat (fn [this-second]
                    (->> reindeers
                         (map #(vector (first %) (simulate-run this-second %)))
                         (group-by second)
                         (sort-by first >)
                         (first)
                         (second)
                         (map first))))
          (frequencies)
          (c/max-by second)))))

