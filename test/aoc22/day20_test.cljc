(ns aoc22.day20-test
  (:require [clojure.test :as t]
            [aoc22.day20 :as day20]))

(t/deftest part1
  (t/testing "input" (t/is (= 2203 (day20/part1 day20/inp-list)))))

(t/deftest part2
  (t/testing "input" (t/is (= 6641234038999 (day20/part2 day20/inp-list)))))


