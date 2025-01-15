(ns aoc15.day25-test
  (:require [clojure.test :as t]
            [aoc15.day25 :as day25]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day25/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day25/part2 "input.txt")))))