(ns aoc22.day21-test
  (:require [clojure.test :as t]
            [aoc22.day21 :as day21]))

(t/deftest part1
  (t/testing "input" (t/is (= 51928383302238 (day21/part1 day21/monkey-math-map)))))

(t/deftest part2
  (t/testing "input" (t/is (= 3305669217840 (day21/part2 day21/monkey-math-map)))))

