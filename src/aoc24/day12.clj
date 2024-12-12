(ns aoc24.day12
  (:require [core :as c]))

(defn find-area [[coords garden] mtx]
  (loop [ncoords [coords]
         area #{coords}]
    (if (empty? ncoords)
      area
      (let [head (first ncoords)
            tail (rest ncoords)
            next-pos (c/up-down-left-right head)
            next-coord (->> next-pos
                            (filter #(= garden (get mtx % :not-found))))]
        (recur (concat tail (c/reject area next-coord))
               (apply conj area next-coord))))))

(defn find-perimeter [area-coords garden mtx]
  (->> area-coords
       (mapcat c/up-down-left-right)
       (map mtx)
       (c/reject #(= garden %))
       (count)))

(c/sum (map #(* (:area %) (:perimeter %)) (let [mtx (c/to-matrix (slurp "./assets/day12/input.txt"))]
  (loop [coords mtx
         visited #{}
         result []]
    (if (empty? coords)
      result
      (let [[coord garden :as head] (first coords)
            tail (rest coords)]
        (if (visited coord)
          (recur tail visited result)
          (let [area (find-area head mtx)
                perimeter (find-perimeter area garden mtx)]
            (recur tail
                   (apply conj visited area)
                   (conj result
                         {:garden (second head)
                          :area (count area)
                          :perimeter perimeter}))))))))))





