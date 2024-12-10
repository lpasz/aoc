(ns aoc24.day20-test
  (:require [clojure.test :as t]
            [aoc24.day20 :as day20]))

(t/deftest part1
  (t/testing "input" (t/is (= (day20/part1 "./assets/day20/input.txt") nil)))
  (t/testing "example" (t/is (= (day20/part1 "./assets/day20/example.txt") nil))))

(t/deftest part2
  (t/testing "input" (t/is (= (day20/part2 "./assets/day20/input.txt") nil)))
  (t/testing "example" (t/is (= (day20/part2 "./assets/day20/example.txt") nil))))

