(ns aoc23.day23-test
  (:require [clojure.test :as t]
            [aoc23.day23 :as day23]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 94 (day23/part1 (c/get-input "example.txt") [1 0] [21 22]))))
  (t/testing "input" (t/is (= 2394 (day23/part1 (c/get-input "input.txt") [1 0] [139 140])))))

(t/deftest part2
  (t/testing "input" (t/is (= 154 (day23/part2 (c/get-input "example.txt") [1 0] [21 22]))))
  (t/testing "input" (t/is (= 6554 (day23/part2 (c/get-input "input.txt") [1 0] [139 140])))))

