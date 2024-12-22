(ns aoc24.day08-test
  (:require [clojure.test :as t]
            [aoc24.day08 :as day08]))

(t/deftest part1
  (t/testing "input" (t/is (= (day08/part1 "input.txt") 259)))
  (t/testing "example" (t/is (= (day08/part1 "example.txt") 14))))

(t/deftest part2
  (t/testing "input" (t/is (= (day08/part2 "input.txt") 927)))
  (t/testing "example" (t/is (= (day08/part2 "example.txt") 34))))

