(ns aoc15.day16-test
  (:require [clojure.test :as t]
            [aoc15.day16 :as day16]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 40 (day16/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 241 (day16/part2 "input.txt")))))
