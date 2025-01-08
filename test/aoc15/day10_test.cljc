(ns aoc15.day10-test
  (:require [clojure.test :as t]
            [aoc15.day10 :as day10]))

(t/deftest part1
  (t/testing "part1" (t/is (= 492982 (day10/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part1" (t/is (= 6989950 (day10/part2 "input.txt")))))

