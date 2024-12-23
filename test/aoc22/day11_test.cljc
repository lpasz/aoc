(ns aoc22.day11-test
  (:require [clojure.test :as t]
            [aoc22.day11 :as day11]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 10605 (day11/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 55458 (day11/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 2713310158 (day11/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 14508081294 (day11/part2 (c/get-input "input.txt"))))))

