(ns aoc23.day16-test
  (:require [clojure.test :as t]
            [aoc23.day16 :as day16]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 46 (day16/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 7034 (day16/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 51 (day16/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 7759 (day16/part2 (c/get-input "input.txt"))))))

