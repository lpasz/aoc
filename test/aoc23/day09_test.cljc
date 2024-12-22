(ns aoc23.day09-test
  (:require [clojure.test :as t]
            [aoc23.day09 :as day09]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 114 (day09/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 1974232246  (day09/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 928 (day09/part2 (c/get-input "input.txt"))))))

