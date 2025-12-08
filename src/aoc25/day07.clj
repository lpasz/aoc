(ns aoc25.day07
  (:require [core :as c]
            [clojure.string :as str]))

(defn propagate-beam [mtx]
  (->> mtx
       (c/filter-by-value #(= \| %))
       (map first)
       (reduce (fn [mtx n]
                 (let [down (c/down n)
                       down-left (c/left down)
                       down-right (c/right down)]
                   (cond (and (= (mtx n) \|) (= (mtx down) \.)) (assoc mtx down \|)
                         (and (= (mtx n) \|) (= (mtx down) \^)) (-> mtx
                                                                    (assoc down-left \|)
                                                                    (assoc down-right \|))
                         :else mtx))) mtx)
       (c/then [new-mtx]
               (if (= new-mtx mtx)
                 mtx
                 (propagate-beam new-mtx)))))

(defn part1 [file-path]
  (let [input (c/get-input file-path)
        mtx (c/to-matrix input)
        [pos _value] (first (c/filter-by-value #(= \S %) mtx))
        down (c/down pos)
        mtx (assoc mtx down \|)
        update-mtx (propagate-beam mtx)]
    (->> update-mtx
         (filter (fn [[k v]]
                   (and (= \^ v)
                        (= \| (update-mtx (c/up k)))
                        (= \| (update-mtx (c/left k)))
                        (= \| (update-mtx (c/right k))))))
         (count))))

(defn upcnt [n cnt]
  (if (nil? n)
    cnt
    (+ n cnt)))

(defn bfs [mtx result]
  (let [result
        (reduce (fn [acc [pos cnt]]
                  (let [down (c/down pos)]
                    (case (mtx down)
                      \. (update acc down #(upcnt % cnt))
                      \^ (-> acc
                             (update (c/left down)  #(upcnt % cnt))
                             (update (c/right down) #(upcnt % cnt)))
                      nil (update acc :total #(upcnt % cnt))))) {} result)]
    (if-let [total (:total result)]
      total
      (bfs mtx result))))

(defn part2 [file-path]
  (let [input (c/get-input file-path)
        mtx (c/to-matrix input)
        [pos _value] (first (c/filter-by-value #(= \S %) mtx))
        down (c/down pos)]
    (bfs mtx {down 1})))
