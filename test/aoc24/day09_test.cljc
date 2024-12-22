(ns aoc24.day09-test
  (:require [clojure.test :as t]
            [aoc24.day09 :as day09]))

(t/deftest part1
  (t/testing "input" (t/is (= (day09/part1 "input.txt") 6430446922192)))
  (t/testing "example" (t/is (= (day09/part1 "example.txt") 1928))))

(t/deftest part2
  (t/testing "input" (t/is (= (day09/part2 "input.txt") 6460170593016)))
  (t/testing "example" (t/is (= (day09/part2 "example.txt") 2858))))

