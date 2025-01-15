(ns aoc15.day24-test
  (:require [clojure.test :as t]
            [aoc15.day24 :as day24]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day24/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day24/part2 "input.txt")))))