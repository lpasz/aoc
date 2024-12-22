(ns aoc22.day15
  (:require [clojure.string :as s]))

(def inp (slurp "../inputs/day-15/inp.txt"))
(def ex-inp (slurp "lib/day-15/ex-inp.txt"))

(defn parse-inp [text]
  (->> (s/split-lines text)
       (mapcat #(s/split % #"(Sensor at x=|, y=|: closest beacon is at x=|, y=)"))
       (filter not-empty)
       (map #(Integer/parseInt %))
       (partition 4)))

(defn manhattan  [x1 y1 x2 y2]
  (+ (abs (- x1 x2)) (abs (- y1 y2))))

(defn find-radius [[sx sy bx by]]
  [[sx sy] (manhattan sx sy bx by)])

;; does the scan radius cross row?
(defn sensor-overlap-with-row? [diff] (pos? diff))

;;    2101234567890123456789012345
;; -2 ..........#.................
;; -1 .........###................
;; +0 ....S...#####...............
;; +1 .......#######........S.....
;; +2 ......#########S............
;; +3 .....###########SB..........
;; +4 ....#############...........
;; +5 ...###############..........
;; +6 ..#################.........
;; +7 .#########S#######S#........
;; +8 ..#################.........
;; +9 ...###############..........
;; 10 ....B############...........  in line 10 sensor [8 7] has and overlap from x 2 to 14 givin it an overlap of 6 
;; 11 ..S..###########............  occuping [(- 8 6) (+ 8 6)] = [2 14]
;; 12 ......#########.............
;; 13 .......#######.............. in line 13 sensor [8 7] has and overlap from x 5 to 10 givin it an overlap of 3
;; 14 ........#####.S.......S..... occuping [(- 8 3) (+ 8 3)] = [5 11]
;; 15 B........###................
;; 16 ..........#SB...............

;;;; PRECALCULAR MAPA PARA CADADA LINHA PODE FAZER FICAR MAIS RAPIDO
;; APENAS LINHAS QUE ELE TOCA


(defn sensor-scan-range-in-row [ranges [[sx sy] radius] row]
  (let [overlap-in-row (- radius (abs (- row sy)))]
    (if (sensor-overlap-with-row? overlap-in-row)
      (conj ranges [(- sx overlap-in-row) (+ sx overlap-in-row)])
      ranges)))

(defn sensors-scan-ranges-in-row [sensors row]
  (->> sensors
       (reduce #(sensor-scan-range-in-row %1 %2 row)  [])
       (sort)
       (vec)))

(defn not-covered-by-scanners [ranges]
  (let [[[start1 end1] [start2 end2]] (take 2 ranges)
        rest-ranges (drop 2 ranges)]
    (cond
      (<= 2 (- start2 end1)) (inc end1)
      (not-empty rest-ranges) (recur (conj rest-ranges [(min start1 end1 start2 end2)
                                                        (max start1 end1 start2 end2)])))))

(defn ppeek [coll] (peek (peek coll)))

(defn ex1 [text row]
  (let [sensors-and-beacons (parse-inp text)
        sensors-and-radius (map find-radius sensors-and-beacons)
        scanned (sensors-scan-ranges-in-row sensors-and-radius row)
        start-of-continuous-range (ffirst scanned)
        end-of-continuous-range (ppeek scanned)]
    (- end-of-continuous-range
       start-of-continuous-range)))


(defn ex2 [text max-val]
  (let [sensors-and-beacons (parse-inp text)
        sensors-and-radius (map find-radius sensors-and-beacons)]
    (loop [y max-val]
      (let [edges (sensors-scan-ranges-in-row sensors-and-radius y)
            x   (not-covered-by-scanners edges)]
        (if x
          (+ (* 4000000 x) y)
          (recur (dec y)))))))


(ex1 ex-inp 10) ;; 26
(ex1 inp 2000000) ;; 5525847
(ex2 ex-inp 20) ;; 56000011
(ex2 inp 4000000) ;; 13340867187704
