(ns aoc24.day15-test
  (:require [clojure.test :as t]
            [aoc24.day15 :as day15]))

(t/deftest part1
  (t/testing "input" (t/is (= (day15/part1 "input.txt") 1465152)))
  (t/testing "example" (t/is (= (day15/part1 "example.txt") 10092)))
  (t/testing "example" (t/is (= (day15/part1 "small-example.txt") 2028))))

(t/deftest part2
  (t/testing "input" (t/is (= (day15/part2 "input.txt") 1511259)))
  (t/testing "example" (t/is (= (day15/part2 "example2.txt") 9021))))

