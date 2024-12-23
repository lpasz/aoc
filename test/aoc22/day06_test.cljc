(ns aoc22.day06-test
  (:require [clojure.test :as t]
            [aoc22.day06 :as day06]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 1538 (day06/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 2315 (day06/part2 (c/get-input "input.txt"))))))

