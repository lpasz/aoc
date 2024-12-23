(ns aoc22.day14-test
  (:require [clojure.test :as t]
            [aoc22.day14 :as day14]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 24 (day14/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 1003 (day14/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 93 (day14/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 25771 (day14/part2 (c/get-input "input.txt"))))))


