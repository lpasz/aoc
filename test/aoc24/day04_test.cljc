(ns aoc24.day04-test
  (:require [clojure.test :as t]
            [aoc24.day04 :as day04]))

(t/deftest part1
  (t/testing "input"
    (t/is (= (day04/part1 "input.txt") 2427)))
  (t/testing "example"
    (t/is (= (day04/part1 "example.txt") 18))))

(t/deftest part2
  (t/testing "input"
    (t/is (= (day04/part2 "input.txt") 1900)))
  (t/testing "example"
    (t/is (= (day04/part2 "example.txt") 9))))

