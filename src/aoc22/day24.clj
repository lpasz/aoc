(ns aoc22.day24
  (:require [clojure.string :as s]
            [clojure.pprint :as pp]))

(def ex-txt (c/get-input "input.txt"))
(def mini-txt (c/get-input "../inputs/day-24/mini.txt"))
(def inp-txt (c/get-input "input.txt"))

(defn draw-board [inp]
  (let [min-x (->> inp (map first) (map first) (apply min))
        max-x (->> inp (map first) (map first) (apply max))
        min-y (->> inp (map first) (map second) (apply min))
        max-y (->> inp (map first) (map second) (apply max))]
    (->> (range min-y (inc max-y))
         (map (fn [y]
                (->> (range min-x (inc max-x))
                     (map (fn [x]
                            (if-let [s (get inp [x y])]
                              s
                              \.)))
                     (apply str))))
         (map println)
         (doall))
    (println)
    inp))

(def blizzard-dir #{\> \< \^ \v})

(defn parse [txt]
  (->> (s/split-lines txt)
       (map-indexed (fn [y line]
                      (->> line
                           (map-indexed (fn [x itm] [[x y] itm])))))
       (mapcat identity)
       (filter #(not= (second %) \.))
       (into (sorted-map))))

(draw-board (parse ex-txt))

(defn warp< [[x y] board]
  (let [[x y] (->> board
                   (keep #(when (and (= \# (second %))
                                     (= y (second (first %))))
                            (first %)))
                   (sort-by first >)
                   (first))]
    [(dec x) y]))

(defn warp> [[x y] board]
  (let [[x y] (->> board
                   (keep #(when (and (= \# (second %))
                                     (= y (second (first %))))
                            (first %)))
                   (sort-by first <)
                   (first))]
    [(inc x) y]))

(defn warp-up [[x y] board]
  (let [[x y] (->> board
                   (keep #(when (and (= \# (second %))
                                     (= x (ffirst %)))
                            (first %)))
                   (sort-by second >)
                   (first))]
    [x (dec y)]))

(defn warp-v [[x y] board]
  (let [[x y] (->> board
                   (keep #(when (and (= \# (second %))
                                     (= x (ffirst %)))
                            (first %)))
                   (sort-by second <)
                   (first))]
    [x (inc y)]))

(defn warp-to [[x y] itm board]
  (case itm
    \< (warp< [x y] board)
    \> (warp> [x y] board)
    \^ (warp-up [x y] board)
    \v (warp-v [x y] board)))

(defn next-pos [[x y] itm board]
  (let [next ((into {} board) [x y])
        warp? (= next \#)]
    (pp/pprint next)
    (if warp?
      (warp-to [x y] itm board)
      [x y])))

(defn move-blizzard [[[x y] itm] board]
  (case itm
    \< (next-pos [(- x 1) y] itm board)
    \> (next-pos [(+ x 1) y] itm board)
    \^ (next-pos [x (- y 1)] itm board)
    \v (next-pos [x (+ y 1)] itm board)))

(defn next-minute [board]
  (->> board
       (map (fn [[_ itm :as b]]
              (if (blizzard-dir itm)
                [(move-blizzard b board) itm]
                b)))))

(next-minute (draw-board (parse mini-txt)))

(loop [start [1 0]
       end [6 5]
       board (parse ex-txt)])