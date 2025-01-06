(ns aoc15.day08-test
  (:require [clojure.test :as t]
            [aoc15.day08 :as day08]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 1342 (aoc15.day08/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 2074 (aoc15.day08/part2 "input.txt")))))
