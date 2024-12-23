(ns aoc22.day09-test
  (:require [clojure.test :as t]
            [aoc22.day09 :as day09]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 5735 (day09/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 2478 (day09/part2 (c/get-input "input.txt"))))))


