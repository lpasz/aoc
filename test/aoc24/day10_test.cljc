(ns aoc24.day10-test
  (:require [clojure.test :as t]
            [aoc24.day10 :as day10]))

(t/deftest part1
  (t/testing "input" (t/is (= (day10/part1 "input.txt") 538)))
  (t/testing "example" (t/is (= (day10/part1 "example.txt") 36))))

(t/deftest part2
  (t/testing "input" (t/is (= (day10/part2 "input.txt") 1110)))
  (t/testing "example" (t/is (= (day10/part2 "example.txt") 81))))

