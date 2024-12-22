(ns aoc24.day18-test
  (:require [clojure.test :as t]
            [aoc24.day18 :as day18]))

(t/deftest part1
  (t/testing "input" (t/is (= (day18/part1 "input.txt" 1024 [70 70]) 262)))
  (t/testing "example" (t/is (= (day18/part1 "example.txt" 12 [6 6]) 22))))

(t/deftest part2
  (t/testing "input" (t/is (= (day18/part2 "input.txt" 1024 [70 70]) '(22 20))))
  (t/testing "example" (t/is (= (day18/part2 "example.txt" 12 [6 6]) '(6 1)))))


