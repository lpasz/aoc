(ns aoc15.day01-test
  (:require [clojure.test :as t]
            [aoc15.day01 :as day01]))

(t/deftest part1
  (t/testing "input" (t/is (= 280 (day01/part1 "input.txt")))))

(t/deftest part2
  (t/testing "input" (t/is (= 1797 (day01/part2 "input.txt")))))

