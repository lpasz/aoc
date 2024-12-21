(ns aoc24.day21-test
  (:require [clojure.test :as t]
            [aoc24.day21 :as day21]))

;;Part 1: 174124
;;Part 2: 216668579770346
(t/deftest part1
  (t/testing "input" (t/is (= (day21/part1 "./assets/day21/input.txt") nil)))
  (t/testing "example" (t/is (= (day21/part1 "./assets/day21/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day21/part2 "./assets/day21/input.txt") nil)))
  (t/testing "example" (t/is (= (day21/part2 "./assets/day21/example.txt") nil))))

