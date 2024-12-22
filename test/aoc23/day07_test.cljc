(ns aoc23.day07-test
  (:require [clojure.test :as t]
            [aoc23.day07 :as day07]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 6440 (day07/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 250453939 (day07/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 5905 (day07/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 248652697 (day07/part2 (c/get-input "input.txt"))))))

