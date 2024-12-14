(ns aoc24.day14
  (:require [core :as c]))

(defn position-after-seconds
  "Give the position of the robot after n second"
  [[x y vx vy] [size-x size-y] seconds]
  (loop [[x y] [x y]
         seconds seconds]
    (if (zero? seconds)
      [x y vx vy]
      (recur (c/next-position [x y vx vy] [size-x size-y])
             (dec seconds)))))

(defn quadrant-calc
  "splits in 4 quadrants and ignore anything on the central cross"
  [[size-x size-y] positions]
  (let [sx (quot size-x 2)
        sy (quot size-y 2)]
    (* (->> positions
            (filter (fn [[x y]] (and (<= 0 x (dec sx))
                                     (<= 0 y (dec sy)))))
            (count))
       (->> positions
            (filter (fn [[x y]] (and (<= 0 x (dec sx))
                                     (<= (inc sy) y (dec size-y)))))
            (count))
       (->> positions
            (filter (fn [[x y]] (and (<= (inc sx) x (dec size-x))
                                     (<= 0 y (dec sy)))))
            (count))
       (->> positions
            (filter (fn [[x y]] (and (<= (inc sx) x (dec size-x))
                                     (<= (inc sy) y (dec size-y)))))
            (count)))))

(defn positions [file]
  (->> (re-seq #"-?\d+" (slurp file))
       (map parse-long)
       (partition 4)))

(defn simulate-movement [file size steps]
  (->> (positions file)
       (map #(position-after-seconds % size steps))))

(defn part1 [file]
  (let [size [101 103]]
    (->> (simulate-movement file size 100)
         (quadrant-calc size))))

(defn xmas-tree?
  "It form a box outside of the xmas tree, we are looking for that straight line
   We are looking for a line of 10 points in sequence.
    ###############################
    #.............................#
    #.............................#
    #.............................#
    #.............................#
    #..............#..............#
    #.............###.............#
    #............#####............#
    #...........#######...........#
    #..........#########..........#
    #............#####............#
    #...........#######...........#
    #..........#########..........#
    #.........###########.........#
    #........#############........#
    #..........#########..........#
    #.........###########.........#
    #........#############........#
    #.......###############.......#
    #......#################......#
    #........#############........#
    #.......###############.......#
    #......#################......#
    #.....###################.....#
    #....#####################....#
    #.............###.............#
    #.............###.............#
    #.............###.............#
    #.............................#
    #.............................#
    #.............................#
    #.............................#
    ###############################
  "
  [positions]
  (loop [positions (sort-by first (sort-by second positions))
         prev-pos nil
         s 0]
    (cond (empty? positions) false
          (nil? prev-pos) (recur (rest positions) (first positions) s)
          :else (let [[px py] prev-pos
                      [cx cy :as curr-pos] (first positions)
                      tail (rest positions)]
                  (cond
                    (= s 31) true
                    (and (= px cx)  (= (inc py) cy)) (recur tail curr-pos (inc s))
                    :else (recur tail curr-pos 0))))))

(defn part2 [file]
  (let [[size-x size-y :as size] [101 103]
        mcnt 8000]
    (loop [cnt 1
           positions (positions file)]
      (if (zero? (- mcnt cnt))
        :not-found
        (let [new-positions (map #(position-after-seconds % size 1) positions)]
          (if (xmas-tree? new-positions)
            (do
              (-> (c/create-matrix size-x size-y \.)
                  (c/add-to-matrix new-positions \#)
                  (c/print-matrix))
              cnt)
            (recur (inc cnt) new-positions)))))))
