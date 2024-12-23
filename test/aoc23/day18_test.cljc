(ns aoc23.day18-test
  (:require [clojure.test :as t]
            [aoc23.day18 :as day18]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 62 (day18/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 62573 (day18/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 952408144115 (day18/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 54662804037719 (day18/part2 (c/get-input "input.txt"))))))

