(ns aoc23.day10-test
  (:require [clojure.test :as t]
            [aoc23.day10 :as day10]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 4 (day10/part1 (c/get-input "example.txt")))))
  (t/testing "example2" (t/is (= 8 (day10/part1 (c/get-input "example2.txt")))))
  (t/testing "input" (t/is (= 7005 (day10/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 1 (day10/part2 (c/get-input "example.txt")))))
  (t/testing "example2" (t/is (= 1 (day10/part2 (c/get-input "example2.txt")))))
  (t/testing "example3" (t/is (= 4 (day10/part2 (c/get-input "example3.txt")))))
  (t/testing "example4" (t/is (= 8 (day10/part2 (c/get-input "example4.txt")))))
  (t/testing "input" (t/is (= 417 (day10/part2 (c/get-input "input.txt"))))))

