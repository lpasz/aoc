(ns aoc15.day13-test
  (:require [clojure.test :as t]
            [aoc15.day13 :as day13]))

(t/deftest part1 (t/testing "part1" (t/is (= 664 (day13/part1 "input.txt")))))
(t/deftest part2 (t/testing "part2" (t/is (= 640 (day13/part2 "input.txt")))))
