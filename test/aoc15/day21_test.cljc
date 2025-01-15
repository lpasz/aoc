(ns aoc15.day21-test
  (:require [clojure.test :as t]
            [aoc15.day21 :as day21]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day21/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day21/part2 "input.txt")))))