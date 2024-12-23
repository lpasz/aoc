(ns aoc22.day03-test
  (:require [clojure.test :as t]
            [aoc22.day03 :as day03]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 157 (day03/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 7980 (day03/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 70 (day03/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 2881 (day03/part2 (c/get-input "input.txt"))))))

