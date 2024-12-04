(ns aoc24.day04-test
  (:require [clojure.test :as t]
            [aoc24.day04 :as day04]))

(t/deftest part1
  (t/testing "input"
    (t/is (= (day04/part1 "./assets/day04/input.txt") 179571322)))
  (t/testing "example"
    (t/is (= (day04/part1 "./assets/day04/example.txt") 161))))

(t/deftest part2
  (t/testing "input"
    (t/is (= (day04/part2 "./assets/day04/input.txt") 103811193)))
  (t/testing "example"
    (t/is (= (day04/part2 "./assets/day04/example2.txt") 48))))

