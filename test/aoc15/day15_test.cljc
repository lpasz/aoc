(ns aoc15.day15-test
  (:require [clojure.test :as t]
            [aoc15.day15 :as day15]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 21367368 (day15/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 1766400 (day15/part2 "input.txt")))))
