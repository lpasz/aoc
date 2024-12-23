(ns aoc22.day19
  (:require [core :as c]
            [clojure.string :as s]))

(defonce ex-inp (c/get-input "input.txt"))
(defonce inp (c/get-input "input.txt"))

(defn parse [text]
  (->>   (s/split text #"[^0-9]")
         (keep not-empty)
         (map #(Integer/parseInt %))
         (partition 7)
         (map (fn [[n oroc croc obroc obrcc groc grobc]]
                {:number n
                 :ore-robot {:ore oroc}
                 :clay-robot {:ore croc}
                 :obisidian-robot {:ore obroc :clay obrcc}
                 :geode-robot {:ore groc :obisidian grobc}}))))

(defonce ex-blueprints (parse ex-inp))
(defonce blueprints (parse inp))

(def robots-collect {:ore-robot :ore
                     :clay-robot :clay
                     :obisidian-robot :obisidian
                     :geode-robot :geode})

(def start-robots {:ore-robot 1
                   :clay-robot 0
                   :obisidian-robot 0
                   :geode-robot 0})

(def start-materials {:ore 0
                      :clay 0
                      :obisidian 0
                      :geode 0})

(def collect-materials (fn [curr-materials robots]
  ;; (pprint [:collect-materials curr-materials robots])
                         (reduce (fn [materials [robot n]]
                                   (let [material (robots-collect robot)]
                                     (update materials material + n)))
                                 curr-materials
                                 robots)))

(def can-make-robot? (fn [robot blueprint available-mats]
                       (if-let [req-mat (get blueprint robot)]
                         (every? (fn [[mat n]]
                                   (let [available (get available-mats mat 0)]
                ;; (pprint [robot available n mat])
                                     (>= available n)))
                                 req-mat)
                         false)))

(can-make-robot? :obisidian-robot (first ex-blueprints) {:ore 4, :clay 17, :obisidian 0, :geode 0})

(def build-robot (fn [robot mat blueprint]
                   (let [cost (get blueprint robot)]
                     (reduce (fn [materials [mat n]]
                               (update materials mat - n))
                             mat
                             cost))))

(def build-robots (fn [mat robots blueprint]
                    (->> [:geode-robot :obisidian-robot :clay-robot :ore-robot]
                         (filter #(can-make-robot? % blueprint mat))
                         (map (fn [robot]
                                (let [nmat (build-robot robot mat blueprint)
                                      nrobots (update robots robot inc)]
                                  [(collect-materials nmat robots) nrobots])))
                         (concat [[(collect-materials mat robots) robots]]))))

(defn heuristics [[mat robots] n]
  (+ (* (:geode mat) 1000)
     (* (:obisidian mat) 15)
     (* (:clay mat) 1)
     (* (:ore mat) 1)
     (* (:geode-robot robots) 1000 (- 32 n))
     (* (:obisidian-robot robots)  15 (- 32 n))
     (* (:clay-robot robots) 1 (- 32 n))
     (* (:ore-robot robots) 1 (- 32 n))))

(defn make-with-blueprint [blueprint q t]
  (let [num q
        blueprint blueprint
        self (atom {0 #{[start-materials start-robots]}})]
    (dotimes [n num]
      (let [next (inc n)]
        (doseq [curr-self (get @self n)]
          (let [[pmat probot] curr-self
                robots (build-robots pmat probot blueprint)]
            (swap! self (fn [self]
                          (update self next
                                  (fn [next]
                                    (->> next
                                         (concat robots)
                                         (set)
                                         (sort-by #(heuristics % n) >)
                                         (take t)))))))))

      (swap! self #(dissoc % n)))
    (get @self num)))

(defn max-geodes [blueprint n t]
  (reduce #(max %1 ((comp :geode first) %2))
          0
          (make-with-blueprint blueprint n t)))

(defn make-blueprints [blueprints n t]
  (reduce #(+ %1 (* (:number %2) (max-geodes %2 n t))) 0 blueprints))

(defn part1 [bp]
  (make-blueprints bp 24 500))

(defn part2 [bp]
  (apply * (map #(max-geodes %1 32 100) (take 3 bp))) ;; 7644
  )
