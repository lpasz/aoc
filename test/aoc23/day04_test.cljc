(ns aoc23.day04-test
  (:require [clojure.test :as t]
            [aoc23.day04 :as day04]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 13 (day04/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 17803 (day04/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 30 (day04/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 5554894 (day04/part2 (c/get-input "input.txt"))))))

