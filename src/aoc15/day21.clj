(ns aoc15.day21
  (:require [core :as c]))

(def me {:hp 100 :dmg 0 :armor 0})

(def items {:weapons [{:name :dagger     :cost 8  :dmg 4 :armor 0}
                      {:name :shortsword :cost 10 :dmg 5 :armor 0}
                      {:name :warhammer  :cost 25 :dmg 6 :armor 0}
                      {:name :longsword  :cost 40 :dmg 7 :armor 0}
                      {:name :greataxe   :cost 74 :dmg 8 :armor 0}]
            :armors [nil
                     {:name :leather    :cost 13  :dmg 0 :armor 1},
                     {:name :chainmail  :cost 31  :dmg 0 :armor 2},
                     {:name :splintmail :cost 53  :dmg 0 :armor 3},
                     {:name :bandedmail :cost 75  :dmg 0 :armor 4},
                     {:name :platemail  :cost 102 :dmg 0 :armor 5}]
            :rings [nil
                    {:name :damage+1  :cost 25  :dmg 1 :armor 0}
                    {:name :damage+2  :cost 50  :dmg 2 :armor 0}
                    {:name :damage+3  :cost 100 :dmg 3 :armor 0}
                    {:name :defense+1 :cost 20  :dmg 0 :armor 1}
                    {:name :defense+2 :cost 40  :dmg 0 :armor 2}
                    {:name :defense+3 :cost 80  :dmg 0 :armor 3}]})

(defn add-item [item me]
  (if (some? item)
    (-> me
        (update :armor #(+ (or % 0) (:armor item)))
        (update :dmg #(+ (or % 0) (:dmg item)))
        (update :cost #(+ (or % 0) (:cost item))))
    me))

(defn attack [attacker attacked]
  (let [dmg (max 1 (- (:dmg attacker) (:armor attacked)))]
    [attacker (update attacked :hp #(max 0 (- %1 dmg)))]))

(defn battle [boss me]
  (let [[me boss] (attack me boss)
        boss-died? (= 0 (:hp boss))
        [boss me] (attack boss me)
        me-died? (= 0 (:hp me))]
    (cond boss-died? {:won (:cost me)}
          me-died? {:lost (:cost me)}
          :else (battle boss me))))

(defn attempt-all-weapons-in-battle [me boss]
  (for [weapon (:weapons items)
        armor (:armors items)
        ring1 (:rings items)
        ring2 (:rings items)
        :when (not= (:name ring1) (:name ring2))]
    (->> me
         (add-item weapon)
         (add-item armor)
         (add-item ring1)
         (add-item ring2)
         (battle boss))))

(defn get-boss [file]
  (let [input (c/get-input file)
        [hitpoints damage armor] (c/extract-numbers input)]
    {:hp hitpoints :dmg damage :armor armor}))

(defn part1 [file]
  (->> (get-boss file)
       (attempt-all-weapons-in-battle me)
       (keep :won)
       (apply min)))

(defn part2 [file]
  (->> (get-boss file)
       (attempt-all-weapons-in-battle me)
       (keep :lost)
       (apply max)))

