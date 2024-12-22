(ns aoc23.day02-test
  (:require [clojure.test :as t]
            [aoc23.day02 :as day02]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 8 (day02/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 2317 (day02/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 2286 (day02/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 74804  (day02/part2 (c/get-input "input.txt"))))))


