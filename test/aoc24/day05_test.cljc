(ns aoc24.day05-test
  (:require [clojure.test :as t]
            [aoc24.day05 :as day05]))

(t/deftest part1
  (t/testing "input"
    (t/is (= (day05/part1 "./assets/day05/input.txt") 7365)))
  (t/testing "example"
    (t/is (= (day05/part1 "./assets/day05/example.txt") 143))))

(t/deftest part2
  (t/testing "input"
    (t/is (= (day05/part2 "./assets/day05/input.txt") 5770)))
  (t/testing "example"
    (t/is (= (day05/part2 "./assets/day05/example.txt") 123))))

