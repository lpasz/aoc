(ns aoc24.day01)

(defn parse-input [file]
  (->> (re-seq #"\d+" (c/get-input file))
       (map parse-long)
       (partition 2)
       (apply map vector)))

(defn part1 [file]
  (->> (parse-input file)
       (map sort)
       ((fn [[a b]] (interleave a b)))
       (partition 2)
       (map (fn [[a b]] (- a b)))
       (map abs)
       (reduce +)))

(defn part2 [file]
  (let [[a b] (parse-input file)
        b (frequencies b)]
    (->> a
         (map #(* % (get b % 0)))
         (reduce +))))

