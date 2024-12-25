(ns aoc24.day25-test
  (:require [clojure.test :as t]
            [aoc24.day25 :as day25]))

(t/deftest part1
  (t/testing "input" (t/is (= (day25/part1 "input.txt") 3671)))
  (t/testing "input" (t/is (= (day25/part1 "example.txt") 3))))
