(ns aoc24.day17-test
  (:require [clojure.test :as t]
            [aoc24.day17 :as day17]))

(t/deftest part1
  (t/testing "input" (t/is (= (day17/part1 "input.txt") [6,5,4,7,1,6,0,3,1])))
  (t/testing "example" (t/is (= (day17/part1 "example.txt") [4,6,3,5,6,3,5,2,1,0]))))

(t/deftest part2
  (t/testing "input" (t/is (= (day17/part2) 106086382266778))))

