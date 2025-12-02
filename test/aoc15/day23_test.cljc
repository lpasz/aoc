(ns aoc15.day23-test
  (:require [clojure.test :as t]
            [aoc15.day23 :as day23]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= :boom (day23/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= :boom (day23/part2 "input.txt")))))
