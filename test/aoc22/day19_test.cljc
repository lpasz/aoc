(ns aoc22.day19-test
  (:require [clojure.test :as t]
            [aoc22.day19 :as day19]))

(t/deftest part1
  (t/testing "input" (t/is (= 1346 (day19/part1 day19/blueprints)))))

(t/deftest part2
  (t/testing "input" (t/is (= 7644 (day19/part2 day19/blueprints)))))

