(ns aoc22.day20
  (:require [clojure.pprint :as pp]))

(def ex-inp (c/get-input "input.txt"))
(def inp (c/get-input "input.txt"))

(def ex-inp-list (read-string (str "[ " ex-inp " ]")))
(def inp-list (read-string (str "[ " inp " ]")))

(defn indexed [coll m]
  (->> coll
       (map-indexed (fn [idx itm]
                      [idx (* m itm)]))
       (vec)))

(defn do-it [coll original]
  (let [itms (java.util.ArrayList. coll)]
    (doseq [[_ value :as itm] original]
      (when (not (zero? value))
        (let [idx (.indexOf itms itm)
              _rem (.remove itms (int idx))
              size (.size itms)
              nidx (cond-> (+ idx value)
                     neg?  (+ size)
                     true  (mod size)
                     zero? (+ size)
                     true  (mod size))]
          (.add itms (int nidx) itm))))
    itms))

(defn calc [coll]
  (->> [1000 2000 3000]
       (map #(nth (cycle coll) (+ (.indexOf coll 0) %)))
       (apply +)))

(defn ex1 [coll]
  (let [idx-itms (indexed coll 1)]
    (->> (do-it idx-itms idx-itms)
         (mapv second))))

(->> ex-inp-list (ex1) calc)
(time (->> inp-list (ex1) calc))

(defn ex2 [coll]
  (let [idx-coll (indexed coll 811589153)
        ddo-it #(do-it %1 idx-coll)]
  (->> (range 0 10)
       (reduce (fn [acc _] 
                 (ddo-it acc)) idx-coll))))

(->> ex-inp-list (ex2) (mapv second) (calc))
(time (->> inp-list (ex2) (mapv second) calc))

