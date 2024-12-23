(ns aoc22.day23-test
  (:require [clojure.test :as t]
            [aoc22.day23 :as day23]))

(t/deftest part1
  (t/testing "input" (t/is (= 4005 (day23/part1 day23/input)))))

(t/deftest part2
  (t/testing "input" (t/is (= 1008 (day23/part2 day23/input)))))
