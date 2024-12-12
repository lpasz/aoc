(ns aoc24.day12-test
  (:require [clojure.test :as t]
            [aoc24.day12 :as day12]))

(t/deftest part1
  (t/testing "input" (t/is (= (day12/part1 "./assets/day12/input.txt") 1449902)))
  (t/testing "example" (t/is (= (day12/part1 "./assets/day12/example.txt") 1930))))

(t/deftest part2
  (t/testing "input" (t/is (= (day12/part2 "./assets/day12/input.txt") 908042)))
  (t/testing "example" (t/is (= (day12/part2 "./assets/day12/example.txt") 1206))))

