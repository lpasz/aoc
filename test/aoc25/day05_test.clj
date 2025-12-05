(ns aoc25.day05-test
  (:require [clojure.test :as t]
            [aoc25.day05 :as day05]))

(t/deftest part1
  (t/testing "Example Input"
    (t/is (= 3 (day05/part1 "example.txt"))))
  (t/testing "Real Input"
    (t/is (= 733 (day05/part1 "input.txt")))))

(t/deftest part2
  (t/testing "Example Input"
    (t/is (= 14 (day05/part2 "example.txt"))))
  (t/testing "Real Input"
    (t/is (= 345821388687084 (day05/part2 "input.txt")))))
