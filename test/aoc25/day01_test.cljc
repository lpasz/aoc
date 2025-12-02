(ns aoc25.day01-test
  (:require [clojure.test :as t]
            [aoc25.day01 :as day01]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 1064 (day01/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 6122 (day01/part2 "input.txt")))))
