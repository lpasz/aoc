(ns aoc22.day16
  (:require [clojure.string :as s]
            [clojure.core.reducers :as r]))

(def inp (c/get-input "input.txt"))
(def ex-inp (c/get-input "input.txt"))

(defn parse-inp [text]
  (->> (s/split-lines text)
       (mapcat #(s/split % #"(Valve | has flow rate=|; tunnels lead to valves |; tunnel leads to valve )"))
       (filter not-empty)
       (partition 3)
       (mapcat (fn [[valve flow-rate tunnels-to]]
                 (if (= flow-rate "0")
                   [[(keyword valve) {:flow-rate (Integer/parseInt flow-rate)
                                      :tunnels-to (mapv keyword (s/split tunnels-to #", "))}]]
                   [[(keyword valve) {:flow-rate 0
                                      :tunnels-to (conj (mapv keyword (s/split tunnels-to #", ")) (keyword (s/lower-case valve)))}]
                    [(keyword (s/lower-case valve)) {:flow-rate (Integer/parseInt flow-rate)
                                                     :tunnels-to [(keyword valve)]}]])))
       (into (sorted-map))))


(def exvalves (parse-inp ex-inp))
(def valves (parse-inp inp))

(defn shortest-path  [graph start end]
  (loop [stack [[0 start]]
         prev-visited #{}
         min-dist 10000000]
    (let [[dist curr-pos] (first stack)
          rstack (rest stack)]
      (cond
        (empty? stack) min-dist
        (= curr-pos end) (recur rstack prev-visited (min dist min-dist))
        :else (let [upstack (if (and (< dist min-dist) (not (prev-visited curr-pos)))
                              (->> graph
                                   (curr-pos)
                                   (:tunnels-to)
                                   (map (fn [t] [(inc dist) t]))
                                   (concat rstack))
                              rstack)]
                (recur upstack (conj prev-visited curr-pos) min-dist))))))

(shortest-path exvalves :AA :bb)

(defn interest-point [graph]
  (keep (fn [[k v]] (if (> (:flow-rate v) 0)
                      k)) graph))

(interest-point exvalves)

(defn from-key-to-key [start graph]
  (let [ip (interest-point graph)
        ipa (conj ip start)]
    (into {} (for [from ipa to ipa :when (not= from to)]
               [[from to] (shortest-path graph from to)]))))

(defn from-key-to-key-p [start graph]
  (let [ip (interest-point graph)
        ipa (conj ip start)]
    (into {} (for [from ipa to ipa :when (not= from to)]
               [[from to]  (* (:flow-rate (to graph)) (- 26 (shortest-path graph from to)))]))))

(defn most-steam [graph start-at timer]
  (let [ip (interest-point graph)
        from-to (from-key-to-key start-at graph)]
    (loop [stack [[(dec timer) start-at #{} 0]]
           max-pressure-release 0]
      (let [[time-rem curr-pos vist pressure] (first stack)
            rstack (rest stack)
            vist (conj vist curr-pos)
            next-poss (filter #(not (vist %1)) ip)]
        (cond
          (empty? stack) max-pressure-release
          (zero? time-rem) (recur rstack (max max-pressure-release pressure))
          (empty? next-poss) (recur rstack (max max-pressure-release pressure))
          :else (let [upstack (->> next-poss
                                   (pmap (fn [next-pos]
                                           (let [time-to-next (from-to [curr-pos next-pos])
                                                 time-rem (- (+ 1 time-rem) time-to-next)]
                                             (if (neg? time-rem)
                                               [0 curr-pos vist pressure]
                                               [time-rem
                                                next-pos
                                                vist
                                                (+ pressure (* time-rem (:flow-rate (next-pos graph))))]))))
                                   (filter #(< max-pressure-release (peek %)))
                                   (concat rstack))]
                  (recur upstack max-pressure-release)))))))

(defn most-steam-paths [graph start-at vist timer]
  (let [ip (interest-point graph)
        from-to (from-key-to-key start-at graph)]
    (loop [stack [[(dec timer) start-at vist 0]]
           paths #{}]
      (let [[time-rem curr-pos vist pressure] (first stack)
            rstack (rest stack)
            vist (conj vist curr-pos)
            next-poss (filter #(not (vist %1)) ip)]
        (cond
          (empty? stack) paths
          (zero? time-rem) (recur rstack
                                  (conj paths [pressure vist]))
          (empty? next-poss) (recur rstack
                                    (conj paths [pressure vist]))
          :else (let [upstack (->> next-poss
                                   (r/map (fn [next-pos]
                                            [next-pos
                                             (- (+ 1 time-rem) (from-to [curr-pos next-pos]))]))
                                   (r/map (fn [[next-pos t1]]
                                            (if (neg? t1)
                                              [0 curr-pos vist pressure]
                                              [t1 next-pos vist
                                               (+ pressure (* t1 (:flow-rate (next-pos graph))))])))
                                   (into #{})
                                   (concat rstack))]
                  (recur upstack paths)))))))

(defn ex1 [vvs]  (most-steam vvs :AA 30))

(time (ex1 exvalves)) ;; 1651
;; (time (ex1 valves)) ;; 1595

(def me-on-track-for-26 (most-steam-paths valves :AA #{} 26))
me-on-track-for-26
;; don't work for example cause by time 26 all will be open by me
(defn ex2 [my-possible-paths]
  (apply max (for [[s path] (take 10 (sort-by first > my-possible-paths))]
               (->> (most-steam-paths valves :AA path 26)
                    (map first)
                    (apply max)
                    (+ s)))))

(ex2 me-on-track-for-26) ;; 2189


;;0     2*     3    4*     6     7    8*    10    11    12    13    14    15    16*   18    19    20*   22    23*  (all valves that can be open are open after this point)
;;AA -> DD -> CC -> BB -> AA -> II -> JJ -> II -> AA -> DD -> EE -> FF -> GG -> HH -> GG -> FF -> EE -> DD -> CC

;;AA  <-+           <-+
;;|    |             |
;;II  BB <-> CC <-> DD <-> EE <-> FF <-> GG <-> HH
;;|
;;JJ


;;Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
;;Valve BB has flow rate=13; tunnels lead to valves CC, AA
;;Valve CC has flow rate=2; tunnels lead to valves DD, BB
;;Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
;;Valve EE has flow rate=3; tunnels lead to valves FF, DD
;;Valve FF has flow rate=0; tunnels lead to valves EE, GG
;;Valve GG has flow rate=0; tunnels lead to valves FF, HH
;;Valve HH has flow rate=22; tunnel leads to valve GG
;;Valve II has flow rate=0; tunnels lead to valves AA, JJ
;;Valve JJ has flow rate=21; tunnel leads to valve II


;;+------------------------+
;;|                        |
;;AA<---------+            |
;;|          |             |
;;II        BB <-> CC <-> DD <-> EE <-> FF <-> GG <-> HH <-> 22
;;|         |      |      |      |
;;JJ        13     2     20      3
;;|
;;21
