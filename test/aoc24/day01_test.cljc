(ns aoc24.day01-test
  (:require [clojure.test :as t]
            [aoc24.day01 :as day01]))

(t/deftest part1
  (t/testing "input" (t/is (= (day01/part1 "./assets/day01/input.txt") 2367773)))
  (t/testing "example" (t/is (= (day01/part1 "./assets/day01/example.txt") 11))))

(t/deftest part2
  (t/testing "input" (t/is (= (day01/part2 "./assets/day01/input.txt") 21271939)))
  (t/testing "example" (t/is (= (day01/part2 "./assets/day01/example.txt") 31))))

