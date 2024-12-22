(ns aoc22.day17
  (:require [clojure.string :as s])
  (:use [clojure.pprint]))

(def ex-inp (c/get-input "input.txt"))
(def inp (c/get-input "input.txt"))

(def ex-winds (cycle (keep #{\> \<} (seq ex-inp))))
(def winds (cycle (keep #{\> \<} (seq inp))))

(def h-line-rock  [2r0011110])

(def cross-rock [2r0001000
                 2r0011100
                 2r0001000])

(def l-rock  [2r0000100
              2r0000100
              2r0011100])

(def v-line-rock  [2r0010000
                   2r0010000
                   2r0010000
                   2r0010000])

(def square-rock  [2r0011000
                   2r0011000])

(def rocks (cycle [h-line-rock
                   cross-rock
                   l-rock
                   v-line-rock
                   square-rock]))

(nth rocks 6)

(def dist-next-rock [2r0000000
                     2r0000000
                     2r0000000])

(def floor-chamber [2r1111111])

(defn prep-falling-rocks [rock chamber]
  (->> (repeat 0)
       (concat rock)
       (take (+ (count rock) (count chamber)))))

(defn prep-existing-rocks [falling-rocks existing-rock]
  (concat (repeat (- (count falling-rocks) (count existing-rock)) 0)
          existing-rock))

(defn prep-rocks [rock existing-rock]
  (let [falling-rocks (prep-falling-rocks rock existing-rock)
        existing-rock (prep-existing-rocks falling-rocks existing-rock)]
    [falling-rocks existing-rock]))

;; (prep-rocks v-line-rock [0 0 0 127])

(defn bit-count [v]
  (let [v (- v (bit-and (bit-shift-right v 1) 0x55555555))
        v (+ (bit-and v 0x33333333) (bit-and (bit-shift-right v 2) 0x33333333))]
    (bit-shift-right (* (bit-and (+ v (bit-shift-right v 4)) 0xF0F0F0F) 0x1010101) 24)))

(def dir {\> bit-shift-right
          \< bit-shift-left})

(defn blow [falling-rocks direction]
  (loop [cfalling-rocks falling-rocks
         wfalling-rocks []]
    (if (empty? cfalling-rocks)
      wfalling-rocks
      (let [slice (first cfalling-rocks)
            rslices (rest cfalling-rocks)
            rocks-in-slice (bit-count slice)
            wslice ((dir direction) slice 1)
            wrocks-in-slice (bit-count wslice)]
        (if (and (= rocks-in-slice wrocks-in-slice)
                 (>= 127 wslice 0))
          (recur rslices (conj wfalling-rocks wslice))
          falling-rocks)))))




(bit-shift-left 2r1111000 1)
2r11110000
;; (blow [2r0010000] \>)
;; (blow [2r0010000] \<)

(defn non-overlapping-rock-count [falling-rocks existing-rocks]
  (->> (map bit-xor existing-rocks falling-rocks)
       (reduce #(+ %1 (bit-count %2)) 0)))

(non-overlapping-rock-count [2r0010000] [2r0001000])
(non-overlapping-rock-count (blow [2r0010000] \>) [2r0001000])
;; (nth ex-winds 33)
;; (blow '(2r0011110 0 0 0 0 0 0 0   0  0   0  0 0  0) (nth ex-winds 33))

(defn wind-flow [falling-rocks existing-rocks wind]
  (let [curr-count (non-overlapping-rock-count existing-rocks falling-rocks)
        bfalling-rocks (blow falling-rocks wind)
        after-count (non-overlapping-rock-count existing-rocks bfalling-rocks)]
    (if (= curr-count after-count)
      bfalling-rocks
      falling-rocks)))


(wind-flow '(2r0011110 0 0 0 0 0 0 0 0 0 0 0 0 0 0) '(0 0 0 0 6 6 4 20 20 124 28 8 30 127) \<)


;; cant move right keep the same way
(= [2r0010000] (wind-flow [2r00010000] [2r0010000] \>))

;; can move left move one
(= [2r0100000] (wind-flow [2r0010000] [2r0001000] \<))


(defn fall [coll]
  (->> coll
       (mapv (fn [n _] (lazy-cat (drop n coll) (take n coll))) (iterate inc 0))
       (peek)))

(fall '(2r0011110 0 0 0 0 0 0 0 0 0 0 0 0 0 0))

(defn gravity-pull [falling-rocks existing-rocks]
  (let [curr-count (non-overlapping-rock-count existing-rocks falling-rocks)
        ffalling-rock (fall falling-rocks)
        after-count (non-overlapping-rock-count existing-rocks ffalling-rock)]
    (if (= curr-count after-count)
      ffalling-rock
      falling-rocks)))


(defn new-existing-rocks [falling-rocks existing-rocks]
  (->> (map bit-or falling-rocks  existing-rocks)
       (filter #(not (zero? %1)))
       (concat dist-next-rock)))

(new-existing-rocks '(2r0011110 0 0 0 0 0 0  0   0   0  0 0  0   0)
                    '(0         0 0 0 6 6 4 20  20 124 28 8 30 127))

(new-existing-rocks '(0 0 0 30 0 0 0  0  0   0  0 0  0   0)
                    '(0 0 0  0 6 6 4 20 20 124 28 8 30 127))

(defn until-hits-floor [falling-rocks existing-rocks wind-num wwinds]
  (loop [cfalling-rocks falling-rocks
         wind-num wind-num]
    (let [wind  (nth wwinds wind-num)
          wfalling-rocks (wind-flow cfalling-rocks existing-rocks wind)
          gfalling-rocks (gravity-pull wfalling-rocks existing-rocks)]
      (if (= wfalling-rocks gfalling-rocks)
        [(inc wind-num) (new-existing-rocks gfalling-rocks existing-rocks)]
        (recur gfalling-rocks (inc wind-num))))))

(until-hits-floor '(2r0011110 0 0 0 0 0 0  0   0   0  0 0  0   0)
                  '(0 0 0 0 6 6 4 20  20 124 28 8 30 127)
                  33
                  ex-winds)

(defn insp [n] (pprint n) n)

(defn place-rocks-until [max-rocks wwinds]
  (loop [existing-rocks (concat dist-next-rock floor-chamber)
         rock-num 0
         wind-num 0]
    (let [rock (nth rocks rock-num)
          [pfalling-rocks pexisting-rocks] (prep-rocks rock existing-rocks)
          [nwind-num nextisting-rocks] (until-hits-floor pfalling-rocks pexisting-rocks wind-num wwinds)]
      (cond (= rock-num (dec max-rocks)) (->> nextisting-rocks (drop 3) (drop-last 1))
            (= 3659 (count nextisting-rocks)) [(inc rock-num) (->> nextisting-rocks (drop 3) (drop-last 1))]
            :else (recur nextisting-rocks  (inc rock-num)  nwind-num)))))

(place-rocks-until 3 ex-winds)

(defn show-rocks [rocks]
  (doseq [rock (map #(s/escape (cl-format nil "~7,'0',B" %1) {\0 \. \1 \#}) rocks)]
    (println (str "|" rock "|")))
  (println "+-------+")
  (print "")
  rocks)

(defonce ex-pile (place-rocks-until 2022 ex-winds))
(defonce pile-2022 (place-rocks-until 2022 winds))
(defonce pile-5000 (place-rocks-until 5000 winds))
(defonce pile-632 (place-rocks-until 632 winds))

;; after initial 632 stones a pattern repeats each 1705 stones piling 2649
(defonce unrepeated-pile (place-rocks-until (rem 1000000000000 1705) winds))

(defonce pile-h-3655 (place-rocks-until 5000 winds))

(- (count pile-632) (count (second pile-h-3655)))


(show-rocks pile-632)
(show-rocks pile-5000)

;; random until 632

(- 2337 632)

;; repeats each 1705 rocks with height of 2649
(+ (* 586510263 2649))

;; 
(+ (* (quot 1000000000000 1705) 2649) (count unrepeated-pile))


;; don't repeat until 1006
;; then repeats each 2649
(- 3656 1007)


(+ (- 7826 5177) 1006)
