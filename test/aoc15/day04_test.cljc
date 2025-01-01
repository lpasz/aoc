(ns aoc15.day04-test
  (:require [clojure.test :as t]
            [aoc15.day04 :as day04]))

(t/deftest part1
  (t/testing "input" (t/is (= 255 (day04/part1 "input.txt")))))

(t/deftest part2
  (t/testing "input" (t/is (= 55 (day04/part2 "input.txt")))))
