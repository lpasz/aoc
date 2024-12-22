(ns aoc23.day12-test
  (:require [clojure.test :as t]
            [aoc23.day12 :as day12]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 21 (day12/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 7939 (day12/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 525152  (day12/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 850504257483930  (day12/part2 (c/get-input "input.txt"))))))

