(ns aoc15.day11-test
  (:require [aoc15.day11 :as day11]
            [clojure.test :as t]))

(t/deftest part1 (t/testing "part1" (t/is (= "hepxxyzz" (day11/part1 "hepxcrrq")))))
(t/deftest part2 (t/testing "part1" (t/is (= "heqaabcc" (day11/part2 "hepxcrrq")))))
