(ns aoc25.day08-test
  (:require [clojure.test :as t]
            [aoc25.day08 :as day08]))

(t/deftest part1
  (t/testing "Example Input"
    (t/is (= 40 (day08/part1 "example.txt" 10))))
  (t/testing "Real Input"
    (t/is (= 52668 (day08/part1 "input.txt")))))

(t/deftest part2
  (t/testing "Example Input"
    (t/is (= 25272 (day08/part2 "example.txt"))))
  (t/testing "Real Input"
    (t/is (= 1474050600 (day08/part2 "input.txt")))))
