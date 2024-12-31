(ns aoc15.day03-test
  (:require [clojure.test :as t]
            [aoc15.day03 :as day03]))

(t/deftest part1
  (t/testing "input" (t/is (= 2572 (day03/part1 "input.txt")))))

(t/deftest part2
  (t/testing "input" (t/is (= 2631 (day03/part2 "input.txt")))))
