(ns aoc22.day12
  (:require [clojure.string :as s]
            [clojure.set :as set]))

(def inp (slurp "input.txt"))
(def ex-inp (slurp "input.txt"))

(defn assoc-start-end [graph]
  (let [start (:start graph)
        ends (:end graph)]
    (-> graph (assoc start \a) (assoc ends \z))))

(defn parse-items [idy itms]
  (map-indexed (fn [idx itm]
                 (cond (= \S itm) [:start [idx idy]]
                       (= \E itm) [:end [idx idy]]
                       :else [[idx idy] itm]))
               itms))

(defn parse-inp [text]
  (->> (s/split-lines text)
       (map-indexed parse-items)
       (reduce concat)
       (into {})
       (assoc-start-end)))

(def ex-graph (parse-inp ex-inp))
(def graph (parse-inp inp))

(parse-inp ex-inp)

(defn possible-paths [curr-pos]
  (let [[curr-x curr-y] curr-pos
        up [curr-x (inc curr-y)]
        down [curr-x (dec curr-y)]
        left  [(dec curr-x) curr-y]
        right [(inc curr-x) curr-y]]
    [up down left right]))

(possible-paths [0 0])

(defn at-most-one-higher? [curr next]
  (let [curr (int curr)
        next (int next)]
    (if (< curr next)
      (<= (abs (- (int curr) (int next))) 1)
      true)))

(at-most-one-higher? \a \a)
(at-most-one-higher? \a \b)
(at-most-one-higher? \a \c)
(at-most-one-higher? \d \a)

(defn can-go-to [curr-pos graph]
  (->> curr-pos
       (possible-paths)
       (filter graph)
       (filter #(at-most-one-higher? (graph curr-pos) (graph %1)))))

(can-go-to (:start ex-graph) ex-graph)


(defn next-steps [step pos graph]
  (->> (can-go-to pos graph)
       (map (fn [next] [(inc step) next]))))


(defn shortest-path
  ([graph]
   (shortest-path graph (:start graph) (:end graph)))
  ([graph start end]
   (loop [stack [[0 start]]
          prev-visited #{}
          paths-to-end []]
     (if (empty? stack)
       paths-to-end
       (let [[step pos] (first stack)
             stack (rest stack)
             visited (conj  prev-visited pos)
             stack (if (not (prev-visited pos))
                     (concat stack (next-steps step pos graph))
                     stack)
             paths-to-end (if (= pos end)
                            (conj paths-to-end [step pos])
                            paths-to-end)]
         (recur stack visited paths-to-end))))))

(time (shortest-path ex-graph))
(time (shortest-path graph))

(defn a-starting-pos [graph]
  (keep (fn [[k val]] (when (= val \a) k)) graph))

(defn shortest-starting-point [graph]
  (->> (a-starting-pos graph)
       (mapcat #(shortest-path graph %1 (:end graph)))
       (sort)
       (ffirst)))

(defn ex1 [text]
  (let [graph (parse-inp text)]
    (ffirst (shortest-path graph (:start graph) (:end graph)))))

(defn ex2 [text]
  (->> (parse-inp text)
       (shortest-starting-point)))

(ex1 ex-inp) ;; 31
(ex1 inp) ;; 394
(ex2 ex-inp) ;; 29
(ex2 inp) ;; 388
