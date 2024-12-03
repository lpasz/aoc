(ns aoc24.day03)

(defn part1 [file]
  (->> (re-seq #"mul\(\d{1,3},\d{1,3}\)" (slurp file))
       (map #(re-seq #"\d{1,3}" %))
       (map (fn [[n1 n2]] (* (parse-long n1) (parse-long n2))))
       (reduce +)))

(defn part2 [file]
  (->> (re-seq #"(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))" (slurp file))
       (map first)
       (reduce (fn [[acc enabled?] itm]
                 (cond (= itm "do()") [acc true]
                       (= itm "don't()") [acc false]
                       enabled? (let [[n1 n2] (re-seq  #"\d{1,3}" itm)]
                                  [(+ acc (* (parse-long n1) (parse-long n2))) enabled?])
                       :else [acc enabled?]))
               [0 true])
       (first)))
