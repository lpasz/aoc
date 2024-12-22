(ns aoc22.day09
  (:require [clojure.string :as s]))

(def inp (slurp "../inputs/day-09/inp.txt"))

(defn move-tail [[hx hy] [tx ty]]
  (let [diff [(- hx tx) (- hy ty)]
        diagonal? (some #(-> % abs (>= 2)) diff)]
    (cond (= [0 2] diff) [tx (inc ty)]
          (= [0 -2] diff) [tx (dec ty)]
          (= [2 0] diff) [(inc tx)  ty]
          (= [-2 0] diff) [(dec tx)  ty]
          (and diagonal? (> hx tx) (> hy ty)) [(inc tx) (inc ty)]
          (and diagonal? (> hx tx) (< hy ty)) [(inc tx) (dec ty)]
          (and diagonal? (< hx tx) (> hy ty)) [(dec tx) (inc ty)]
          (and diagonal? (< hx tx) (< hy ty)) [(dec tx) (dec ty)]
          :else [tx ty])))

(move-tail [3 1] [1 1])

(defn move-head [dir [hx hy]]
  (cond (= dir "R") [(inc hx) hy]
        (= dir "L") [(dec hx) hy]
        (= dir "U") [hx (inc hy)]
        (= dir "D") [hx (dec hy)]))

(move-head "R" [1 1])

(move-tail (move-head "D" [6 4]) [5 4])

(defn move [h t [dir steps]]
  (reduce (fn [[prev-h prev-t visited] _]
            (let [next-h (move-head dir prev-h)
                  next-t (move-tail next-h prev-t)]
              [next-h next-t (conj visited next-t)]))
          [h t #{}]
          (range 1 (inc steps))))

(move [1 1] [1 1] ["R" 4])

(defn move-long-step [head tails]
  (loop [head head
         tails tails
         result [head]]
    (let [tail (first tails)
          tails (rest tails)
          next-t (move-tail head tail)
          result (conj result next-t)]
      (if (empty? tails)
        result
        (recur
         next-t
         tails
         result)))))

(defn move-long [curr-pos [dir steps]]
  (reduce (fn [[curr-pos visited] _]
            (let [next-h (move-head dir (first curr-pos))
                  new-pos (move-long-step  next-h (rest curr-pos))]
              [new-pos (conj visited (last new-pos))]))
          [curr-pos #{}]
          (range 1 (inc steps))))

(move-long-step [2 1] (repeat 8 [1 1]))

(move-long (repeat 9 [1 1]) ["R" 5])

(def ex-inp (str "R 4\n"
                 "U 4\n"
                 "L 3\n"
                 "D 1\n"
                 "R 4\n"
                 "D 1\n"
                 "L 5\n"
                 "R 2"))

(def ex-inp2 (str "R 5\n"
                  "U 8\n"
                  "L 8\n"
                  "D 3\n"
                  "R 17\n"
                  "D 10\n"
                  "L 25\n"
                  "U 20"))


(defn parse-inp [text]
  (->> (s/split-lines text)
       (map #(->> (s/split % #" ")
                  ((fn [[dir steps]]
                     [dir (Integer/parseInt steps)]))))))

(defn ex1 [text]
  (->> (parse-inp text)
       (reduce (fn [[h-pos t-pos visited] next-h-move]
                 (let [[next-h next-t next-visited] (move h-pos t-pos next-h-move)]
                   [next-h next-t (into #{} (concat visited next-visited))]))
               [[1 1] [1 1] #{[1 1]}])
       (last)
       (count)))

(ex1 ex-inp)
(ex1 inp)

(defn ex2 [text]
  (->> (parse-inp text)
       (reduce (fn [[curr-pos visited] next-h-move]
                 (let [[new-pos next-visited] (move-long curr-pos next-h-move)]
                   [new-pos (into #{} (concat visited next-visited))]))
               [(repeat 10 [1 1]) #{[1 1]}])
       (last)
       (count)))

(ex2 ex-inp2)
(ex2 inp)


