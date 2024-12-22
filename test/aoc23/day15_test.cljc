(ns aoc23.day15-test
  (:require [clojure.test :as t]
            [aoc23.day15 :as day15]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 1320 (day15/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 517965   (day15/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 145 (day15/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 267372  (day15/part2 (c/get-input "input.txt"))))))

