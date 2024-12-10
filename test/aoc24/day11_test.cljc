(ns aoc24.day11-test
  (:require [clojure.test :as t]
            [aoc24.day11 :as day11]))

(t/deftest part1
  (t/testing "input" (t/is (= (day11/part1 "./assets/day11/input.txt") nil)))
  (t/testing "example" (t/is (= (day11/part1 "./assets/day11/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day11/part2 "./assets/day11/input.txt") nil)))
  (t/testing "example" (t/is (= (day11/part2 "./assets/day11/example.txt") nil))))

