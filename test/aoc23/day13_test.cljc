(ns aoc23.day13-test
  (:require [clojure.test :as t]
            [aoc23.day13 :as day13]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 405 (day13/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 29846 (day13/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 400 (day13/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 25401 (day13/part2 (c/get-input "input.txt"))))))

