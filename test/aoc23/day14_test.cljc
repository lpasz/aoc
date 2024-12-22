(ns aoc23.day14-test
  (:require [clojure.test :as t]
            [aoc23.day14 :as day14]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 136 (day14/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 109665  (day14/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 64 (day14/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 96061 (day14/part2 (c/get-input "input.txt"))))))

