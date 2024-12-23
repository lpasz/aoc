(ns aoc22.day08-test
  (:require [clojure.test :as t]
            [aoc22.day08 :as day08]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 1679 (day08/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 536625 (day08/part2 (c/get-input "input.txt"))))))


