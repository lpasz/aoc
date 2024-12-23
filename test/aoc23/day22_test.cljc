(ns aoc23.day22-test
  (:require [clojure.test :as t]
            [aoc23.day22 :as day22]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 5 (day22/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 393 (day22/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 7 (day22/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 58440 (day22/part2 (c/get-input "input.txt"))))))

