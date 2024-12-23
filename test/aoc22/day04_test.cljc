(ns aoc22.day04-test
  (:require [clojure.test :as t]
            [aoc22.day04 :as day04]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 2 (day04/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 595 (day04/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 4 (day04/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 952 (day04/part2 (c/get-input "input.txt"))))))


