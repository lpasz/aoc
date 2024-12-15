(ns aoc24.day15
  (:require [core :as c]
            [clojure.string :as s]))

(defn can-move? [mtx dir coord]
  (let [next-coord (dir (c/directions coord))
        next-itm (mtx next-coord)]
    (case next-itm
      \. true
      \# false
      \O (can-move? mtx dir next-coord))))

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
        \O (-> mtx
               (assoc curr-coord prev-itm)
               (attempt-move dir next-coord curr-itm))))))

(defn part1 [file]
  (let [[board moves] (s/split (slurp file) #"\n\n")
        board (c/to-matrix board)
        moves (c/movements moves)
        robot-coord (c/find-matrix-coord-of board \@)]
    (->> (loop [moves moves
                mtx board
                robot-coord robot-coord]
           (if (empty? moves)
             mtx
             (let [mtx (attempt-move mtx
                                     (first moves)
                                     robot-coord
                                     \.)]
               (recur (rest moves)
                      mtx
                      (c/find-matrix-coord-of mtx \@)))))
         (c/filter-by-value #(= \O %))
         (map (fn [[[x y] _]] (+ (* 100 y) x)))
         (c/sum))))

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
      (case next-itm
        \. true
        \# false
        \[ (wide-can-move? mtx dir next-coord)
        \] (wide-can-move? mtx dir next-coord))
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
        (case next-itm
          \. (-> mtx
                 (assoc curr-coord prev-itm)
                 (assoc next-coord curr-itm))
          \[ (-> mtx
                 (assoc curr-coord prev-itm)
                 (wide-attempt-move dir next-coord curr-itm))
          \] (-> mtx
                 (assoc curr-coord prev-itm)
                 (wide-attempt-move dir next-coord curr-itm)))
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
  (let [[board moves] (s/split (slurp file) #"\n\n")
        board (upscale board)
        board (c/to-matrix board)
        robot-coord (c/find-matrix-coord-of board \@)
        moves (c/movements moves)]
    (->> (loop [moves moves
                mtx board
                robot-coord robot-coord]
           (if (empty? moves)
             mtx
             (let [mtx (wide-attempt-move mtx
                                          (first moves)
                                          robot-coord
                                          \.)]
               (recur (rest moves)
                      mtx
                      (c/find-matrix-coord-of mtx \@)))))

         (c/filter-by-value #(= \[ %))
         (map (fn [[[x y] _]] (+ (* 100 y) x)))
         (c/sum))))

