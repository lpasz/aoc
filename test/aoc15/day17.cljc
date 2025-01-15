(ns aoc15.day17-test
  (:require [clojure.test :as t]
            [aoc15.day17 :as day17]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day17/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day17/part2 "input.txt")))))