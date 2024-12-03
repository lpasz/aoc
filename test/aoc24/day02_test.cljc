(ns aoc24.day02-test
  (:require [clojure.test :as t]
            [aoc24.day02 :as day02]))

(t/deftest part1
  (t/testing "input"
    (t/is (= (day02/part1 "./assets/day02/input.txt") 670)))
  (t/testing "example"
    (t/is (= (day02/part1 "./assets/day02/example.txt") 2))))

(t/deftest part2
  (t/testing "input"
    (t/is (= (day02/part2 "./assets/day02/input.txt") 700)))
  (t/testing "example"
    (t/is (= (day02/part2 "./assets/day02/example.txt") 4))))

