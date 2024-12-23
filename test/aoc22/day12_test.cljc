(ns aoc22.day12-test
  (:require [clojure.test :as t]
            [aoc22.day12 :as day12]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 31 (day12/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 394 (day12/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 29 (day12/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 388 (day12/part2 (c/get-input "input.txt"))))))

