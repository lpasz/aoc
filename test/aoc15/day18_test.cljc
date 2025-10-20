(ns aoc15.day18-test
  (:require [clojure.test :as t]
            [aoc15.day18 :as day18]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 768 (day18/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 781 (day18/part2 "input.txt")))))
