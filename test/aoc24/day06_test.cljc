(ns aoc24.day06-test
  (:require [clojure.test :as t]
            [aoc24.day06 :as day06]))

(t/deftest part1
  (t/testing "input"
    (t/is (= (day06/part1 "./assets/day06/input.txt") 5131)))
  (t/testing "example"
    (t/is (= (day06/part1 "./assets/day06/example.txt") 41))))

(t/deftest part2
  ;; (t/testing "input" (t/is (= (day06/part2 "./assets/day06/input.txt") 1784)))
  (t/testing "example"
    (t/is (= (day06/part2 "./assets/day06/example.txt") 6))))

