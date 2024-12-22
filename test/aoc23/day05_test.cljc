(ns aoc23.day05-test
  (:require [clojure.test :as t]
            [aoc23.day05 :as day05]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 35 (day05/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 323142486 (day05/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 46 (day05/part2 (c/get-input "example.txt") 1))))
  (t/testing "input" (t/is (= 79874951 (day05/part2 (c/get-input "input.txt") 5000)))))

