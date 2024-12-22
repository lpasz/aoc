(ns aoc24.day21-test
  (:require [clojure.test :as t]
            [aoc24.day21 :as day21]))

(t/deftest part1
  (t/testing "input" (t/is (= (day21/part1 "./assets/day21/input.txt") 174124)))
  (t/testing "example" (t/is (= (day21/part1 "./assets/day21/example.txt") 126384))))

(t/deftest part2
  (t/testing "input" (t/is (= (day21/part2 "./assets/day21/input.txt") 216668579770346)))
  (t/testing "example" (t/is (= (day21/part2 "./assets/day21/example.txt") 154115708116294))))

