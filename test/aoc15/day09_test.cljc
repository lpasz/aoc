(ns aoc15.day09-test
  (:require [clojure.test :as t]
            [aoc15.day09 :as day09]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 605 (day09/part1 "example.txt")))
    (t/is (= 141 (day09/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 982 (day09/part2 "example.txt")))
    (t/is (= 736 (day09/part2 "input.txt")))))
