(ns aoc22.day06)

(defn idx-first-distinct-seq [n coll]
  (loop [seq coll i 0]
    (if (apply distinct? (take n seq))
      (+ i n)
      (recur (rest seq) (inc i)))))

(defn part1 [text] (idx-first-distinct-seq 4 text))
(defn part2 [text] (idx-first-distinct-seq 14 text))

