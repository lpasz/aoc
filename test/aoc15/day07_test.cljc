(ns aoc15.day07-test
  (:require [clojure.test :as t]
            [aoc15.day06 :as day06]))

(t/deftest part1
  (t/testing "input" (t/is (= 956 (day06/part1 "input.txt")))))

(t/deftest part2
  (t/testing "input" (t/is (= 40149 (day06/part2 "input-with-a.txt")))))
