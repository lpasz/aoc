(ns aoc23.day19-test
  (:require [clojure.test :as t]
            [aoc23.day19 :as day19]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 19114 (day19/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 575412 (day19/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 167409079868000 (day19/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 126107942006821 (day19/part2 (c/get-input "input.txt"))))))

