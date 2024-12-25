(ns aoc24.day24
  (:require [core :as c]
            [clojure.string :as s]))

(defn part2 [file] :not-implemented)

(defn parse-input [file]
  (let [i (c/get-input file)
        [v t] (s/split i #"\n\n")
        v (->> (re-seq #"(\w+?\d+): (\d+)" v)
               (map next)
               (map (c/fnvec keyword parse-long))
               (into {}))
        t (->> (re-seq #"(\w+(\d+)?) (\w+) (\w+(\d+)?) -> (\w+(\d+)?)" t)
               (map next)
               (map #(map keyword (c/reject nil? %))))]
    {:values v :operations t}))

(defn produce-wire-result [file]
  (let [{:keys [values operations]} (parse-input file)]
    (loop [values values operations operations]
      (if (every? number? operations)
        [values operations]
        (let [[values operations] (reduce (fn [[values operations] itm]
                                            (if (number? itm)
                                              [values (conj operations itm)]
                                              (let [[k1 op k2 k3] itm
                                                    v1 (values k1)
                                                    v2 (values k2)
                                                    v3 (when (and (number? v1) (number? v2))
                                                         (case op
                                                           :AND (bit-and v1 v2)
                                                           :XOR (bit-xor v1 v2)
                                                           :OR  (bit-or v1 v2)))]
                                                (if v3
                                                  [(assoc values k3 v3) (conj operations v3)]
                                                  [values (conj operations itm)])))) [values []] operations)]
          (recur values operations))))))

(defn part1 [file]
  (->> (produce-wire-result file)
       (first)
       ((fn [m]
          (let [z-nums (->> (keys m)
                            (map str)
                            (filter #(s/starts-with? % ":z"))
                            (map #(re-seq #"\d+" %))
                            (map first)
                            (sort))]
            (loop [z-nums z-nums r '()]
              (if (empty? z-nums)
                r
                (let [z-num (first z-nums)
                      z-rest (rest z-nums)]
                  (recur z-rest (conj r (m (keyword (str "z" z-num)))))))))))
       (apply str)
       (#(Long/parseLong % 2))))

(defn make-key [letter n]
  (keyword (str letter (format "%02d" n))))

(def != not=)

(defn verify-direct-carry [wire n formula]
  (c/insp :verify-direct-carry  [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-direct-carry  [o x y])
    (if (!= o :AND) false
        (= (sort [x y]) (map #(make-key % n) ["x" "y"])))))

(defn verify-intermediate-xor [wire n formula]
  (c/insp :verify-intermediate-xor [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-intermediate-xor [o x y])
    (if (!= o :XOR) false
        (= (sort [x y]) (map #(make-key % n) ["x" "y"])))))

(defn verify-carry-bit [wire n formula]
  (c/insp :verify-carry-bit [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-carry-bit [o x y])
    (cond (and (c/one? n) (not= o :AND)) false
          (c/one? n) (= (sort [x y]) (map #(make-key % (dec n)) ["x" "y"]))
          (not= o :OR) false
          :else (or (and (verify-direct-carry x (dec n) formula)
                         (verify-recarry y (dec n) formula))
                    (and (verify-direct-carry y (dec n) formula)
                         (verify-recarry x (dec n) formula))))))

(defn verify-recarry [wire n formula]
  (c/insp :verify-recarry [wire n])
  (let [[o x y] (formula wire)]
    (if (!= o :AND) false
        (or (and (verify-intermediate-xor x n formula)
                 (verify-carry-bit y n formula))
            (and (verify-intermediate-xor  y n formula)
                 (verify-carry-bit x n formula))))))

(defn verify-z [wire n formula]
  (c/insp :verify-z [wire n])
  (let [[o x y] (formula wire)]
    (c/insp :verify-z [o x y])
    (cond (not= o :XOR) false
          (zero? n) (= (sort [x y]) [:x00 :y00])
          :else (or (and (verify-intermediate-xor  x n formula)
                         (verify-carry-bit y n formula))
                    (and (verify-intermediate-xor y n formula)
                         (verify-carry-bit x n formula))))))

(let [{:keys [operations]} (parse-input "input.txt")
      formulas (->> operations (map (fn [[x o y z]] [z [o x y]])) (into (sorted-map)))]
  (loop [n (range 0 46)]
    (if (verify-z (make-key "z" (first n)) (first n) formulas)
      (recur (rest n))
      (first n))))


(s/join "," (sort ["z16""hmk"
"fhp""z20"
"tpc""rvf"
"z33""fcd"]))
;; z16 <-> hmk
;; fhp <-> z20
;; tpc <-> rvf
;; z33 <-> fcd
