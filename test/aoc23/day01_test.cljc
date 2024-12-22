(ns aoc23.day01-test
  (:require [clojure.test :as t]
            [aoc23.day01 :as day01]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 142 (day01/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 54450 (day01/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 281 (day01/part2 (c/get-input "example2.txt")))))
  (t/testing "input" (t/is (= 54265 (day01/part2 (c/get-input "input.txt"))))))
