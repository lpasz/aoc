(ns aoc24.day25-test
  (:require [clojure.test :as t]
            [aoc24.day25 :as day25]))

(t/deftest part1
  (t/testing "input" (t/is (= (day25/part1 "./assets/day25/input.txt") nil)))
  (t/testing "example" (t/is (= (day25/part1 "./assets/day25/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day25/part2 "./assets/day25/input.txt") nil)))
  (t/testing "example" (t/is (= (day25/part2 "./assets/day25/example.txt") nil))))

