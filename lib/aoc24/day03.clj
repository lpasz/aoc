(ns aoc24.day03)

(defn mul [expr]
  (->> expr
       (re-seq #"\d{1,3}")
       (map parse-long)
       (reduce *)))

(defn part1 [file]
  (->> (re-seq #"mul\(\d{1,3},\d{1,3}\)" (slurp file))
       (map mul)
       (reduce +)))

(defn part2 [file]
  (->> (re-seq #"(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))" (slurp file))
       (map first)
       (reduce
        (fn [[acc enabled?] itm]
          (cond (= itm "do()") [acc true]
                (= itm "don't()") [acc false]
                enabled? [(+ acc (mul itm)) enabled?]
                :else [acc enabled?]))
        [0 true])
       (first)))
