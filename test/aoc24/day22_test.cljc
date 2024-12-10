(ns aoc24.day22-test
  (:require [clojure.test :as t]
            [aoc24.day22 :as day22]))

(t/deftest part1
  (t/testing "input" (t/is (= (day22/part1 "./assets/day22/input.txt") nil)))
  (t/testing "example" (t/is (= (day22/part1 "./assets/day22/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day22/part2 "./assets/day22/input.txt") nil)))
  (t/testing "example" (t/is (= (day22/part2 "./assets/day22/example.txt") nil))))

