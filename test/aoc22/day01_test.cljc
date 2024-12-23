(ns aoc22.day01-test
  (:require [clojure.test :as t]
            [aoc22.day01 :as day01]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 69528 (day01/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 206152 (day01/part2 (c/get-input "input.txt"))))))

