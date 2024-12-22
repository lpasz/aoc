(ns aoc22.day06)

(defn idx-first-distinct-seq [n coll]
  (loop [seq coll i 0]
    (if (apply distinct? (take n seq))
      (+ i n)
      (recur (rest seq) (inc i)))))

(def inp-path "../inputs/day-06/inp.txt")
(def inp (slurp inp-path))
(defn ex1 [text] (idx-first-distinct-seq 4 text))
(defn ex2 [text] (idx-first-distinct-seq 14 text))

(ex1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")    ;; 7
(ex2 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")    ;; 19
(ex1 "bvwbjplbgvbhsrlpgdmjqwftvncz")      ;; 5 
(ex2 "bvwbjplbgvbhsrlpgdmjqwftvncz")      ;; 23
(ex1 "nppdvjthqldpwncqszvftbrmjlhg")      ;; 6
(ex2 "nppdvjthqldpwncqszvftbrmjlhg")      ;; 23
(ex1 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") ;; 10
(ex2 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") ;; 29
(ex1 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")  ;; 11
(ex2 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")  ;; 26
(time (ex1 inp)) ;; 1538
(time (ex2 inp)) ;; 2315