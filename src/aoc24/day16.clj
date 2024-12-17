(ns aoc24.day16
  (:require [core :as c]))

(def rotations
  {:->   [[:-> 1]   [:up 1001] [:down 1001]]
   :<-   [[:<- 1]   [:up 1001] [:down 1001]]
   :up   [[:up 1]   [:<- 1001] [:-> 1001]]
   :down [[:down 1] [:<- 1001] [:-> 1001]]})

(defn next-coord-with-cost [[curr-cost curr-idx curr-coord curr-dir curr-visited] mtx]
  (let [dirs (c/directions curr-coord)]
    (->> (rotations curr-dir)
         (map (fn [[dir cost]]
                (let [cost (+ cost curr-cost)
                      coord (dir dirs)]
                  [cost
                   (inc curr-idx)
                   coord
                   dir
                   (conj curr-visited curr-coord)])))
         (c/reject #(= \# (mtx (nth % 2)))))))

(defn update-best-cost-to-coord
  [best-cost-to-coord [curr-cost _ curr-coord _ curr-visited]]
  (update best-cost-to-coord
          curr-coord
          (fn [v]
            (let [[lowest-cost paths] v]
              (cond (nil? v) [curr-cost [curr-visited]]
                    (= lowest-cost  curr-cost) [lowest-cost (conj paths curr-visited)]
                    (> lowest-cost curr-cost) [curr-cost [curr-visited]]
                    (< lowest-cost curr-cost) v)))))

(defn shortest-path [mtx start end]
  (loop [stack (sorted-set [0 0 start :-> []])
         best-cost-to-coord {}
         prev-visited #{}
         paths-to-end []]
    (if (empty? stack)
      [paths-to-end best-cost-to-coord]
      (let [[curr-cost _ curr-coord curr-dir curr-visited :as curr] (first stack)
            stack (disj stack curr)
            best-cost-to-coord (update-best-cost-to-coord best-cost-to-coord curr)
            visited (conj prev-visited [curr-coord curr-dir])
            stack (if (not (prev-visited [curr-coord curr-dir]))
                    (reduce #(conj %1 %2) stack (next-coord-with-cost curr mtx))
                    stack)
            paths-to-end (if (= end curr-coord)
                           (conj paths-to-end [curr-cost (conj curr-visited end)])
                           paths-to-end)]
        (recur stack best-cost-to-coord visited paths-to-end)))))

(defn part1 [file]
  (let [mtx (c/to-matrix (slurp file))
        start-at (c/find-matrix-coord-of mtx \S)
        ends-at (c/find-matrix-coord-of mtx \E)]
    (->> (shortest-path mtx start-at ends-at)
         (first)
         (sort-by first)
         (ffirst))))

(defn part2 [file]
  (let [mtx (c/to-matrix (slurp file))
        start-at (c/find-matrix-coord-of mtx \S)
        ends-at (c/find-matrix-coord-of mtx \E)
        [paths-to-end best-cost-to-coord] (shortest-path mtx start-at ends-at)]
    (->> (sort-by first paths-to-end)
         (first)
         (second)
         (mapcat #(second (best-cost-to-coord %)))
         (c/flatten-once)
         ;; add the ends-at so we don't get a one off error 
         (concat [ends-at])
         (set)
         (count))))

