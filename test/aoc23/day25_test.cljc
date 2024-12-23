(ns aoc23.day25-test
  (:require [clojure.test :as t]
            [aoc23.day25 :as day25]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 54 (day25/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 543834 (day25/part1 (c/get-input "input.txt"))))))

