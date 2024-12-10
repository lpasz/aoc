(ns aoc24.day13-test
  (:require [clojure.test :as t]
            [aoc24.day13 :as day13]))

(t/deftest part1
  (t/testing "input" (t/is (= (day13/part1 "./assets/day13/input.txt") nil)))
  (t/testing "example" (t/is (= (day13/part1 "./assets/day13/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day13/part2 "./assets/day13/input.txt") nil)))
  (t/testing "example" (t/is (= (day13/part2 "./assets/day13/example.txt") nil))))

