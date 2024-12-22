(ns aoc24.day18
  (:require [core :as c]))

(defn after-n-bytes-shortest-path [file n-bytes [x y :as size]]
  (let [nums (c/extract-numbers (c/get-input file))
        pairs (take n-bytes (partition 2 nums))
        mtx (c/create-matrix (inc x) (inc y) \.)
        mtx (c/add-to-matrix mtx pairs \#)
        graph (c/matrix-to-graph mtx)
        dijk  (c/dijkstra-shortest graph [0 0] size)]
    [(last pairs) (get dijk size)]))

(defn part1 [file size after-n-bytes]
  (second (after-n-bytes-shortest-path file after-n-bytes size)))

(defn part2 [file size start-byte]
  (let [nums (c/extract-numbers (c/get-input file))]
    (loop [rng (range start-byte (count nums))]
      (let [[fh sh] (split-at (quot (count rng) 2) rng)
            shh (first sh)
            sht (rest sh)
            [_coord1 dist1] (after-n-bytes-shortest-path file shh size)
            [coord2 dist2] (after-n-bytes-shortest-path file (inc shh) size)
            where [(infinite? dist1) (infinite? dist2)]]
        (case where
          [true true] (recur fh)
          [false false] (recur sht)
          [false true] coord2)))))

