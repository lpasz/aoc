(ns aoc24.day18-test
  (:require [clojure.test :as t]
            [aoc24.day18 :as day18]))

(t/deftest part1
  (t/testing "input" (t/is (= (day18/part1) 262))))

(t/deftest part2
  (t/testing "input" (t/is (= (day18/part2) '(22 20)))))

