(ns aoc25.day04-test
  (:require [clojure.test :as t]
            [aoc25.day04 :as day04]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 13 (day04/part1 "example.txt")))
    (t/is (= 1502 (day04/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 43 (day04/part2 "example.txt")))
    (t/is (= 9083 (day04/part2 "input.txt")))))
