(ns aoc15.day02-test
  (:require [clojure.test :as t]
            [aoc15.day02 :as day02]))

(t/deftest part1
  (t/testing "input" (t/is (= 1588178 (day02/part1 "input.txt")))))

(t/deftest part2
  (t/testing "input" (t/is (= 3783758 (day02/part2 "input.txt")))))

