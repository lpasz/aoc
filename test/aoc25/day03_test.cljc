(ns aoc25.day03-test
  (:require [clojure.test :as t]
            [aoc25.day03 :as day03]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 357 (day03/part1 "example.txt")))
    (t/is (= :boom (day03/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day03/part2 "input.txt")))))
