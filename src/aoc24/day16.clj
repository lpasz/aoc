(ns aoc24.day16
  (:require [core :as c]))

(defn part1 [file] :not-implemented)

(defn part2 [file] :not-implemented)

(def example "./assets/day16/example.txt")
(def example2 "./assets/day16/example2.txt")
(def input "./assets/day16/input.txt")

(def rotations
  {:->   [[:-> 1]   [:up 1001] [:down 1001]]
   :<-   [[:<- 1]   [:up 1001] [:down 1001]]
   :up   [[:up 1]   [:<- 1001] [:-> 1001]]
   :down [[:down 1] [:<- 1001] [:-> 1001]]})

(defn next-coord-with-cost [[curr-coord curr-dir curr-cost] mtx]
  (let [dirs (c/directions curr-coord)]
    (->> (rotations curr-dir)
         (map (fn [[dir cost]] [(dir dirs) dir (+ cost curr-cost)]))
         (c/reject #(= \# (mtx (first %)))))))

(defn shortest-path [mtx start end]
  (loop [stack [[start :-> 0]]
         prev-visited #{}
         paths-to-end []]
    (if (empty? stack)
      paths-to-end
      (let [[curr-coord _curr-dir curr-cost :as curr] (first stack)
            stack (rest stack)
            visited (conj prev-visited curr-coord)
            stack (if (not (prev-visited curr-coord))
                    (sort-by last (concat stack (next-coord-with-cost curr mtx)))
                    stack)
            paths-to-end (if (= end curr-coord)
                           (conj paths-to-end curr-cost)
                           paths-to-end)]
        (recur stack visited paths-to-end)))))

(let [text (slurp input)
      mtx (c/to-matrix text)
      start-at (c/find-matrix-coord-of mtx \S)
      ends-at (c/find-matrix-coord-of mtx \E)]
  (->> (shortest-path mtx start-at ends-at)
       (apply min)))



