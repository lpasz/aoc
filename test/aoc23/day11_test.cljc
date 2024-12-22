(ns aoc23.day11-test
  (:require [clojure.test :as t]
            [aoc23.day11 :as day11]
            [core :as c]))
(t/deftest part1
  (t/testing "example" (t/is (= 374 (day11/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 9648398  (day11/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 618800410814 (day11/part2 (c/get-input "input.txt"))))))

