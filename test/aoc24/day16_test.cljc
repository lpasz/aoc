(ns aoc24.day16-test
  (:require [clojure.test :as t]
            [aoc24.day16 :as day16]))

(t/deftest part1
  (t/testing "input" (t/is (= (day16/part1 "./assets/day16/input.txt") nil)))
  (t/testing "example" (t/is (= (day16/part1 "./assets/day16/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day16/part2 "./assets/day16/input.txt") nil)))
  (t/testing "example" (t/is (= (day16/part2 "./assets/day16/example.txt") nil))))

