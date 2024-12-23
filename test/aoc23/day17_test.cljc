(ns aoc23.day17-test
  (:require [clojure.test :as t]
            [aoc23.day17 :as day17]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 102 (day17/part1 (c/get-input "example.txt") [12 12]))))
  (t/testing "input" (t/is (= 742 (day17/part1 (c/get-input "input.txt") [140 140])))))

(t/deftest part2
  (t/testing "example" (t/is (= 94 (day17/part2 (c/get-input "example.txt") [12 12]))))
  (t/testing "input" (t/is (= 918 (day17/part2 (c/get-input "input.txt") [140 140])))))

