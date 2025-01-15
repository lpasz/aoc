(ns aoc15.day20-test
  (:require [clojure.test :as t]
            [aoc15.day20 :as day20]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day20/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day20/part2 "input.txt")))))