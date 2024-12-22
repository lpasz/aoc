(ns aoc24.day03-test
  (:require [clojure.test :as t]
            [aoc24.day03 :as day03]))

(t/deftest part1
  (t/testing "input"
    (t/is (= (day03/part1 "input.txt") 179571322)))
  (t/testing "example"
    (t/is (= (day03/part1 "example.txt") 161))))

(t/deftest part2
  (t/testing "input"
    (t/is (= (day03/part2 "input.txt") 103811193)))
  (t/testing "example"
    (t/is (= (day03/part2 "example2.txt") 48))))

