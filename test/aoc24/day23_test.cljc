(ns aoc24.day23-test
  (:require [clojure.test :as t]
            [aoc24.day23 :as day23]))

(t/deftest part1
  (t/testing "input" (t/is (= (day23/part1 "./assets/day23/input.txt") nil)))
  (t/testing "example" (t/is (= (day23/part1 "./assets/day23/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day23/part2 "./assets/day23/input.txt") nil)))
  (t/testing "example" (t/is (= (day23/part2 "./assets/day23/example.txt") nil))))

