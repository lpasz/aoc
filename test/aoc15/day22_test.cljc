(ns aoc15.day22-test
  (:require [clojure.test :as t]
            [aoc15.day22 :as day22]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day22/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day22/part2 "input.txt")))))