(ns aoc24.day14-test
  (:require [clojure.test :as t]
            [aoc24.day14 :as day14]))

(t/deftest part1
  (t/testing "input" (t/is (= (day14/part1 "./assets/day14/input.txt") 211773366))))

(t/deftest part2
  (t/testing "input" (t/is (= (day14/part2 "./assets/day14/input.txt") 7344))))

