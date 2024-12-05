(ns aoc24.day05
  (:require [core :as c]
            [clojure.string :as s]))

(defn parse-input [file]
  (let [text (slurp file)
        [rules pages] (s/split text #"\n\n")
        rules (->> (s/split rules #"\n")
                   (map #(s/split % #"\|"))
                   (map reverse)
                   (map #(mapv parse-long %))
                   (group-by first)
                   (map (fn [[a b]] [a (set (map second b))]))
                   (into {}))
        pages (->> (s/split pages #"\n")
                   (map #(s/split % #","))
                   (map #(map parse-long %)))]
    [rules pages]))

(defn ordered-page [page rules]
  (sort-by #(count (filter (get rules % #{}) page)) page))

(defn middle-of-page-num [page]
  (nth page (quot (count page) 2)))

(defn part1 [file]
  (let [[rules pages] (parse-input file)]
    (->> pages
         (filter #(= (ordered-page % rules) %))
         (map middle-of-page-num)
         (c/sum))))

(defn part2 [file]
  (let [[rules pages] (parse-input file)]
    (->> pages
         (keep (fn [page]
                 (->> (ordered-page page rules)
                      (c/then [ordered-page]
                              (when (not= page ordered-page)
                                (middle-of-page-num ordered-page))))))
         (c/sum))))




