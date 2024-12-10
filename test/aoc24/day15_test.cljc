(ns aoc24.day15-test
  (:require [clojure.test :as t]
            [aoc24.day15 :as day15]))

(t/deftest part1
  (t/testing "input" (t/is (= (day15/part1 "./assets/day15/input.txt") nil)))
  (t/testing "example" (t/is (= (day15/part1 "./assets/day15/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day15/part2 "./assets/day15/input.txt") nil)))
  (t/testing "example" (t/is (= (day15/part2 "./assets/day15/example.txt") nil))))

