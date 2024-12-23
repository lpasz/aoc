(ns aoc22.day22-test
  (:require [clojure.test :as t]
            [aoc22.day22 :as day22]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 67390 (day22/part1 (c/get-input "input.txt") [51 1])))))

(t/deftest part2
  (t/testing "input" (t/is (= 95291 (day22/part2 (c/get-input "input.txt") [51 1] day22/faces-borders)))))

