(ns aoc24.day24
  (:require [core :as c]
            [clojure.string :as s]))

(defn part1 [file] :not-implemented)

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

(->> (produce-wire-result "input.txt")
     (first)
     ((fn [m]
        (let [z-nums (->> (keys m)
                          (map str)
                          (filter #(s/starts-with? % ":z"))
                          (map #(re-seq #"\d+" %))
                          (map first)
                          (sort)
                          (c/insp))]
          (loop [z-nums z-nums r '()]
            (if (empty? z-nums)
              r
              (let [z-num (first z-nums)
                    z-rest (rest z-nums)]
                (recur z-rest (conj r (m (keyword (str "z" z-num)))))))))))
     (apply str)
     (#(Long/parseLong % 2)))



