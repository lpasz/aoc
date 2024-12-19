(ns aoc24.day19-test
  (:require [clojure.test :as t]
            [aoc24.day19 :as day19]))

(t/deftest part1
  (t/testing "input" (t/is (= (day19/part1 "./assets/day19/input.txt") 242)))
  (t/testing "example" (t/is (= (day19/part1 "./assets/day19/example.txt") 6))))

(t/deftest part2
  (t/testing "input" (t/is (= (day19/part2 "./assets/day19/input.txt") 595975512785325)))
  (t/testing "example" (t/is (= (day19/part2 "./assets/day19/example.txt") 16))))

