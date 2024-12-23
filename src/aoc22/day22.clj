(ns aoc22.day22
  (:require [clojure.string :as s]
            [core :as c]))

(def ex-inp (c/get-input "input.txt"))
(def inp (c/get-input "input.txt"))

(defn y-boundaries [mmap]
  (->> (group-by ffirst mmap)
       (map (fn [[x vals]]
              (let [ys (map #(second (first %1)) vals)
                    mx (apply max ys)
                    mn (apply min ys)]
                [[x :warp] [mx mn]])))
       (into {})))

(defn x-boundaries [mmap]
  (->> (group-by #(second (first %1)) mmap)
       (map (fn [[y vals]]
              (let [xs (map ffirst vals)
                    mx (apply max xs)
                    mn (apply min xs)]
                [[:warp y] [mx mn]])))
       (into {})))

(defn parse-mmap [text]
  (->> (s/split-lines text)
       (keep-indexed (fn [idy line]
                       (->> line
                            (keep-indexed (fn [idx itm] (if (not= \space itm)
                                                          [[(inc idx) (inc idy)] itm]))))))
       (mapcat identity)
       (into (sorted-map))))

(defn parse-dir [dirs]
  (->> (partition-by #{\R \L} (seq dirs))
       (map (fn [[num :as nums]] (if (#{\R \L} num)
                                   num
                                   (Integer/parseInt (apply str nums)))))
       (flatten)))

(defn parse [text]
  (let [[mmap mdir] (s/split text #"\n\n")
        mmap (parse-mmap mmap)]
    [(parse-dir (s/replace mdir "\n" ""))
     mmap
     (x-boundaries mmap)
     (y-boundaries mmap)]))

(def shift {[:right \R] :down
            [:right \L] :up
            [:left \R] :up
            [:left \L] :down
            [:up \R] :right
            [:up \L] :left
            [:down \R] :left
            [:down \L] :right})

(defn walk [[x y] dir]
  (cond (= dir :right) [(inc x)      y]
        (= dir :left)  [(dec x)      y]
        (= dir :up)    [x       (dec y)]
        (= dir :down)  [x       (inc y)]))

(defn warp-x [[x y] xb]
  (first (filter #(not= x %1) (xb [:warp y]))))

(defn warp-y [[x y] yb]
  (first (filter #(not= y %1) (yb [x :warp]))))

(defn warp-to [[x y :as curr-pos] dir xb yb]
  (if (#{:left :right} dir)
    [(warp-x curr-pos xb) y]
    [x (warp-y curr-pos yb)]))

(def sides {:side-4 [[1 50] [101 150]]
            :side-6 [[1 50] [151 200]]
            :side-1 [[51 100] [1 50]]
            :side-3 [[51 100] [51 100]]
            :side-5 [[51 100] [101 150]]
            :side-2 [[101 150] [1 50]]})

(def side-cubes {:side-1 {:up {:warp :side-6 :on :left :headed :right :reverse false}
                          :down {:warp :side-3 :on :up :headed :down :reverse false}
                          :left {:warp :side-4 :on :left :headed :right :reverse true}
                          :right {:warp :side-2 :on :left :headed :right :reverse false}}
                 :side-2 {:up {:warp :side-6 :on :down :headed :up :reverse false}
                          :down {:warp :side-3 :on :right :headed :left :reverse false}
                          :left {:warp :side-1 :on :right :headed :left :reverse false}
                          :right {:warp :side-5 :on :right :headed :left :reverse true}}
                 :side-3 {:up {:warp :side-1 :on :down :headed :up :reverse false}
                          :down {:warp :side-5 :on :up :headed :down :reverse false}
                          :left {:warp :side-4 :on :up :headed :down :reverse false}
                          :right {:warp :side-2 :on :down :headed :up :reverse false}}
                 :side-4 {:up {:warp :side-3 :on :left :headed :right :reverse false}
                          :down {:warp :side-6 :on :up :headed :down :reverse false}
                          :left {:warp :side-1 :on :left :headed :right :reverse true}
                          :right {:warp :side-5 :on :left :headed :right :reverse false}}
                 :side-5 {:up {:warp :side-3 :on :down :headed :up :reverse false}
                          :down {:warp :side-6 :on :right :headed :left :reverse false}
                          :left {:warp :side-4 :on :right :headed :left :reverse false}
                          :right {:warp :side-2 :on :right :headed :left :reverse true}}
                 :side-6 {:up {:warp :side-4 :on :down :headed :up :reverse false}
                          :down {:warp :side-2 :on :up :headed :down :reverse false}
                          :left {:warp :side-1 :on :up :headed :down :reverse false}
                          :right {:warp :side-5 :on :down :headed :up :reverse false}}})

(defn side [[x y]]
  (->> {:side-1 [[51 100] [1 50]]
        :side-2 [[101 150] [1 50]]
        :side-3 [[51 100] [51 100]]
        :side-4 [[1 50] [101 150]]
        :side-5 [[51 100] [101 150]]
        :side-6 [[1 50] [151 200]]}
       (filter (fn [[_ [[x1 x2] [y1 y2]]]]
                 (and (<= x1 x x2)
                      (<= y1 y y2))))
       (ffirst)))

(def rev (zipmap (range 1 51) (range 50 0 -1)))

(defn downsize [n]
  (cond (<= n 50) n
        (<= n 100) (- n 50)
        (<= n 150) (- n 100)
        (<= n 200) (- n 150)))

(defn warp-to-cube [[x y :as curr-pos] dir borders]
  (let [side (side curr-pos)
        {:keys [warp on headed reverse] :as map} (get-in side-cubes [side dir])
        equivalent-position (downsize (if (#{:left :right} dir) y x))
        equivalent-position (if reverse (rev equivalent-position) equivalent-position)]
    [headed (get-in borders [warp on equivalent-position])]))

(defn walk-dir [curr-pos steps dir mmap warper]
  (if (#{\R \L} steps)
    [curr-pos (shift [dir steps])]
    (loop [curr-pos curr-pos
           steps steps
           dir dir]
      (let [next-pos (walk curr-pos dir)
            next-block (mmap next-pos)
            blocked? (= \# next-block)
            warp? (= nil next-block)
            [wcdir warp-to] (warper curr-pos dir)
            warp-to-block (mmap warp-to)
            warp-blocked? (= \# warp-to-block)]
        (cond
          (zero? steps) [curr-pos dir]
          blocked? [curr-pos dir]
          (and (nil? next-block) warp-blocked?) [curr-pos dir]
          (and warp? (some? warp-to-block)) (recur warp-to (dec steps) wcdir)
          :else (recur next-pos (dec steps) dir))))))

(defn walk-the-line [curr-pos direction dirs mmap warper]
  (loop [curr-pos curr-pos
         direction direction
         dirs dirs]
    (if (empty? dirs)
      [curr-pos direction]
      (let [[steps & dirs] dirs
            [next-pos direction] (walk-dir curr-pos steps direction mmap warper)]
        (recur next-pos direction dirs)))))

(def dir-points {:right 0
                 :down 1
                 :left 2
                 :up 3})

(defn part1 [text start]
  (let [[dirs mmap xb yb] (parse text)
        warper #(do [%2 (warp-to %1 %2 xb yb)])
        [[col row] direction] (walk-the-line start
                                             :right
                                             dirs
                                             mmap
                                             warper)]
    (+ (* 1000 row) (* 4 col) (dir-points direction))))

(defn part2 [text start borders]
  (let [[dirs mmap] (parse text)
        warper #(warp-to-cube %1 %2 borders)
        [[col row] direction] (walk-the-line start
                                             :right
                                             dirs
                                             mmap
                                             warper)]
    (+ (* 1000 row) (* 4 col) (dir-points direction))))

(defn border [v]
  (let [[x1 x2] (first v)
        [y1 y2] (second v)]
    {:up (into (sorted-map) (map-indexed (fn [idx x]    [(inc idx) [x y1]]) (range x1 (inc x2))))
     :down (into (sorted-map)  (map-indexed (fn [idx x] [(inc idx) [x y2]]) (range x1 (inc x2))))
     :left (into (sorted-map)  (map-indexed (fn [idx y] [(inc idx) [x1 y]]) (range y1 (inc y2))))
     :right (into (sorted-map) (map-indexed (fn [idx y] [(inc idx) [x2 y]]) (range y1 (inc y2))))}))

(def faces-borders (into (sorted-map) (map (fn [[k v]] [k (border v)]) sides)))


