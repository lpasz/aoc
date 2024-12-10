(ns aoc24.day24-test
  (:require [clojure.test :as t]
            [aoc24.day24 :as day24]))

(t/deftest part1
  (t/testing "input" (t/is (= (day24/part1 "./assets/day24/input.txt") nil)))
  (t/testing "example" (t/is (= (day24/part1 "./assets/day24/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day24/part2 "./assets/day24/input.txt") nil)))
  (t/testing "example" (t/is (= (day24/part2 "./assets/day24/example.txt") nil))))

