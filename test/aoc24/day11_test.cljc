(ns aoc24.day11-test
  (:require [clojure.test :as t]
            [aoc24.day11 :as day11]))

(t/deftest part1
  (t/testing "input" (t/is (= (day11/part1 "./assets/day11/input.txt") 217812)))
  (t/testing "example" (t/is (= (day11/part1 "./assets/day11/example.txt") 55312))))

(t/deftest part2
  (t/testing "input" (t/is (= (day11/part2 "./assets/day11/input.txt") 259112729857522)))
  (t/testing "example" (t/is (= (day11/part2 "./assets/day11/example.txt") 65601038650482))))

