(ns aoc15.day12-test
  (:require [aoc15.day12 :as day12]
            [clojure.test :as t]))

(t/deftest part1 (t/testing "part1" (t/is (= 111754 (day12/part1 "input.txt")))))

(t/deftest part2 (t/testing "part2" (t/is (= 65402 (day12/part2 "input.txt")))))
