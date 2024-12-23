(ns aoc22.day13-test
  (:require [clojure.test :as t]
            [aoc22.day13 :as day13]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 13(day13/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 5390 (day13/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 140 (day13/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 19261 (day13/part2 (c/get-input "input.txt"))))))

