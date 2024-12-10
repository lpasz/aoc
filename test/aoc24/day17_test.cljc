(ns aoc24.day17-test
  (:require [clojure.test :as t]
            [aoc24.day17 :as day17]))

(t/deftest part1
  (t/testing "input" (t/is (= (day17/part1 "./assets/day17/input.txt") nil)))
  (t/testing "example" (t/is (= (day17/part1 "./assets/day17/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day17/part2 "./assets/day17/input.txt") nil)))
  (t/testing "example" (t/is (= (day17/part2 "./assets/day17/example.txt") nil))))

