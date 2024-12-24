(ns aoc24.day24-test
  (:require [clojure.test :as t]
            [aoc24.day24 :as day24]))

(t/deftest part1
  (t/testing "input" (t/is (= (day24/part1 "input.txt") 66055249060558)))
  (t/testing "example" (t/is (= (day24/part1 "example.txt") 2024))))

(t/deftest part2
  (t/testing "input" (t/is (= (day24/part2 "input.txt") nil)))
  (t/testing "example" (t/is (= (day24/part2 "example.txt") nil))))

