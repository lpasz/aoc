(ns aoc24.day15
  (:require [core :as c]
            [clojure.string :as s]))

(defn can-move? [mtx dir coord]
  (let [next-coord (dir (c/directions coord))
        next-itm (mtx next-coord)]
    (case next-itm
      \. true
      \# false
      (can-move? mtx dir next-coord))))

(defn attempt-move [mtx dir curr-coord prev-itm]
  (if (not (can-move? mtx dir curr-coord))
    mtx
    (let [curr-itm (mtx curr-coord)
          next-coord (dir (c/directions curr-coord))
          next-itm (mtx next-coord)]
      (case next-itm
        \. (-> mtx
               (assoc curr-coord prev-itm)
               (assoc next-coord curr-itm))
        (-> mtx
            (assoc curr-coord prev-itm)
            (attempt-move dir next-coord curr-itm))))))

(defn calculate-box-distance-idx [mtx]
  (->> mtx
       (c/filter-by-value #{\[ \O})
       (map (fn [[[x y] _]] (+ (* 100 y) x)))
       (c/sum)))

(defn apply-moves [mtx moves robot-coord move-fn]
  (if (empty? moves)
    mtx
    (let [mtx (move-fn mtx
                       (first moves)
                       robot-coord
                       \.)]
      (recur mtx
             (rest moves)
             (c/find-matrix-coord-of mtx \@)
             move-fn))))

(defn part1 [file]
  (let [[board moves] (s/split (c/get-input file) #"\n\n")
        board (c/to-matrix board)
        moves (c/movements moves)
        robot-coord (c/find-matrix-coord-of board \@)]
    (->> (apply-moves board moves robot-coord attempt-move)
         (calculate-box-distance-idx))))

(defn upscale [board]
  (->> board
       (s/split-lines)
       (map (fn [line]
              (->> line
                   (mapcat (fn [itm]
                             (case itm
                               \# [\# \#]
                               \. [\. \.]
                               \@ [\@ \.]
                               \O [\[ \]]))))))))

(defn wide-can-move? [mtx dir coord]
  (let [next-coord (dir (c/directions coord))
        next-itm (mtx next-coord)]
    (if (#{:<- :->} dir)
      (can-move? mtx dir coord)
      (case next-itm
        \. true
        \# false
        \[ (and (wide-can-move? mtx dir next-coord)
                (wide-can-move? mtx dir (:-> (c/directions next-coord))))
        \] (and (wide-can-move? mtx dir next-coord)
                (wide-can-move? mtx dir (:<- (c/directions next-coord))))))))

(defn wide-attempt-move [mtx dir curr-coord prev-itm]
  (if (not (wide-can-move? mtx dir curr-coord))
    mtx
    (let [curr-itm (mtx curr-coord)
          next-coord (dir (c/directions curr-coord))
          next-itm (mtx next-coord)]
      (if (#{:<- :->} dir)
        (attempt-move mtx dir curr-coord prev-itm)
        (case next-itm
          \. (-> mtx
                 (assoc curr-coord prev-itm)
                 (assoc next-coord curr-itm))
          \[ (-> mtx
                 (assoc curr-coord prev-itm)
                 (wide-attempt-move dir next-coord curr-itm)
                 (wide-attempt-move dir
                                    (:-> (c/directions next-coord))
                                    \.))
          \] (-> mtx
                 (assoc curr-coord prev-itm)
                 (wide-attempt-move dir next-coord curr-itm)
                 (wide-attempt-move dir
                                    (:<- (c/directions next-coord))
                                    \.)))))))

(defn part2 [file]
  (let [[board moves] (s/split (c/get-input file) #"\n\n")
        board (upscale board)
        board (c/to-matrix board)
        robot-coord (c/find-matrix-coord-of board \@)
        moves (c/movements moves)]
    (->> (apply-moves board moves robot-coord wide-attempt-move)
         (calculate-box-distance-idx))))

