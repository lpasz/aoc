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
       (mapcat #(map (fn [[d c]] [d c %]) (c/directions %)))
       (c/reject #(= garden (mtx (second %))))
       (map (fn [[dir _ org]] [dir org]))
       ;;
       ))

(defn find-garden-info [file]
  (let [mtx (c/to-matrix (slurp file))]
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
                            :perimeter (count perimeter)
                            :discount-perimeter perimeter})))))))))
(defn part1 [file]
  (->> (find-garden-info file)
       (map #(* (:area %) (:perimeter %)))
       (c/sum)))

(defn by-direction-sorter [dir]
  (fn [[x1 y1] [x2 y2]]
    (if (#{:up :down} dir)
      (if (= y1 y2)
        (compare x1 x2)
        (compare y1 y2))
      (if (= x1 x2)
        (compare y1 y2)
        (compare x1 x2)))))

(defn join-straight-fences [ls]
  (loop [i 0 itms ls]
    (if (empty? itms)
      i
      (let [head (first itms)
            tail (rest itms)]
        (if ((set (c/up-down-left-right head)) (first tail))
          (recur i tail)
          (recur (inc i) tail))))))

(defn sort-fences-by-direction [[dir itms]]
  (->> itms
       (map second)
       (sort (by-direction-sorter dir))))

(defn part2 [file]
  (let [garden-infos (find-garden-info file)]
    (->> (for [info garden-infos]
           (->> info
                (:discount-perimeter)
                (group-by first)
                (map sort-fences-by-direction)
                (map join-straight-fences)
                (c/sum)
                (* (:area info))))
         (c/sum))))

