(ns aoc23.day06-test
  (:require [clojure.test :as t]
            [aoc23.day06 :as day06]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 288 (day06/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 1195150 (day06/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 71503 (day06/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 42550411 (day06/part2 (c/get-input "input.txt"))))))

