(ns aoc22.day02-test
  (:require [clojure.test :as t]
            [aoc22.day02 :as day02]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 14531 (day02/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 11258 (day02/part2 (c/get-input "input.txt"))))))


