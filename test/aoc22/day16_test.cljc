(ns aoc22.day16-test
  (:require [clojure.test :as t]
            [aoc22.day16 :as day16]))

(t/deftest part1
  (t/testing "input" (t/is (= 1595 (day16/part1 day16/valves)))))

(t/deftest part2
  (t/testing "input" (t/is (= 2189 (day16/part2 day16/me-on-track-for-26)))))

