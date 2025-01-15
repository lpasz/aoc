(ns aoc15.day19-test
  (:require [clojure.test :as t]
            [aoc15.day19 :as day19]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day19/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day19/part2 "input.txt")))))