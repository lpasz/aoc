(ns aoc23.day21-test
  (:require [clojure.test :as t]
            [aoc23.day21 :as day21]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 16 (day21/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 3776 (day21/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 625587097150084  (day21/part2)))))

