(ns aoc25.day04
  (:require [core :as c]))

(defn parse [file]
  (->> file
       (c/get-input)
       (c/to-matrix)))

(defn fork-liftable [mtx]
  (->> mtx
       (c/filter-by-value #(= \@ %))
       (c/filter-by-key
        (fn [coord]
          (->> coord
               (c/around)
               (keep mtx)
               (filter #(= \@ %))
               (count)
               (c/then [cnt] (> 4 cnt)))))
       (map first)))

(defn part1 [file]
  (let [mtx (parse file)]
    (->> (fork-liftable mtx)
         (count))))

(defn part2 [file]
  (let [mtx (parse file)]
    (loop [mtx mtx
           cnt 0]
      (let [to-fork (fork-liftable mtx)
            to-fork-cnt (count to-fork)
            cnt (+ cnt to-fork-cnt)
            after-forked (reduce (fn [acc coord] (assoc acc coord \.))
                                 mtx
                                 to-fork)]
        (if (zero? to-fork-cnt)
          cnt
          (recur after-forked cnt))))))

