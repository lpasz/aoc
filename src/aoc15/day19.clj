(ns aoc15.day19
  (:require [core :as c]
            [clojure.string :as s]))

(defn prepare-input [file]
  (let [input (c/get-input file)
        [p1 p2] (s/split input #"\n\n")
        rep (->> p1
                 (s/split-lines)
                 (map (fn [line] (s/split line #" => "))))
        text (s/trim p2)]
    [rep text]))

(defn replacements [text k v]
  (loop [text text
         before ""
         results #{}]
    (cond
      (empty? text)
      results

      (s/starts-with? text k)
      (let [new (str before (s/replace-first text k v))
            head (apply str (take 1 text))
            tail (apply str (drop 1 text))]
        (recur tail (str before head) (conj results new)))

      :else
      (let [head (apply str (take 1 text))
            tail (apply str (drop 1 text))]
        (recur  tail (str before head) results)))))

(defn calibrated [reps text]
  (loop [reps reps
         result #{}]
    (if (empty? reps)
      result
      (let [[[k v] & rest] reps]
        (recur rest (into result (replacements text k v)))))))

(defn part1 [file]
  (let [[reps text] (prepare-input file)]
    (count (calibrated reps text))))

(defn sorter [[_ ^String a] [_ ^String b]]
  (let [cmp1 (compare (.length a) (.length b))]
    (if (zero? cmp1)
      (compare a b)
      cmp1)))

(defn part2 [file]
  (let [[reps molecule] (prepare-input file)]
    (loop [visited #{}
           results (sorted-set-by sorter [0 molecule])]
      (let [head (first results)
            rest (disj results head)]
        (cond
          (nil? head) :error
          (visited head) (recur visited rest)
          :else
          (let [[round molecule] head]
            (if (= molecule "e")
              round
              (recur (conj visited head)
                     (reduce
                      (fn [acc [to from]]
                        (let [cnt (c/includes-count molecule from)]
                          (if (zero? cnt)
                            acc
                            (reduce
                             (fn [acc n]
                               (conj acc [(inc round) (c/replace-nth molecule from to n)]))
                             acc
                             (c/inc-range 1 cnt)))))
                      rest
                      reps)))))))))

