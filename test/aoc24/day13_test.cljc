(ns aoc24.day13-test
  (:require [clojure.test :as t]
            [aoc24.day13 :as day13]))

(t/deftest part1
  (t/testing "input" (t/is (= (day13/part1 "input.txt") 34787)))
  (t/testing "example" (t/is (= (day13/part1 "example.txt") 480))))

(t/deftest part2
  (t/testing "input" (t/is (= (day13/part2 "input.txt") 85644161121698N)))
  (t/testing "example" (t/is (= (day13/part2 "example.txt") 875318608908N))))

