(ns aoc24.day18-test
  (:require [clojure.test :as t]
            [aoc24.day18 :as day18]))

(t/deftest part1
  (t/testing "input" (t/is (= (day18/part1 "./assets/day18/input.txt") nil)))
  (t/testing "example" (t/is (= (day18/part1 "./assets/day18/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day18/part2 "./assets/day18/input.txt") nil)))
  (t/testing "example" (t/is (= (day18/part2 "./assets/day18/example.txt") nil))))

