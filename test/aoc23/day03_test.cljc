(ns aoc23.day03-test
  (:require [clojure.test :as t]
            [aoc23.day03 :as day03]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 4361 (day03/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 533775  (day03/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 467835  (day03/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 78236071   (day03/part2 (c/get-input "input.txt"))))))

