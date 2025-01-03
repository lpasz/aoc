(ns aoc15.day05-test
  (:require [clojure.test :as t]
            [aoc15.day05 :as day05]))

(t/deftest part1
  (t/testing "input" (t/is (= 255 (day05/part1 "input.txt")))))

(t/deftest part2
  (t/testing "input" (t/is (= 55 (day05/part2 "input.txt")))))
