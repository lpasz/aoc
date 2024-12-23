(ns aoc23.day24-test
  (:require [clojure.test :as t]
            [aoc23.day24 :as day24]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 2 (day24/part1 (c/get-input "example.txt") day24/exp1-rng))))
  (t/testing "input" (t/is (= 12740 (day24/part1 (c/get-input "input.txt") day24/part1-rng)))))

(t/deftest part2
  (t/testing "input" (t/is (= 47 (day24/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 741991571910536  (day24/part2 (c/get-input "input.txt"))))))

