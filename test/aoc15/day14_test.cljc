(ns aoc15.day14-test
  (:require [aoc15.day14 :as day14]
            [clojure.test :as t]))

(t/deftest part1 (t/testing "part1" (t/is (= 2696 (day14/part1 "input.txt")))))
(t/deftest part2 (t/testing "part2" (t/is (= 1084 (day14/part2 "input.txt")))))
