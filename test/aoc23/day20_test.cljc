(ns aoc23.day20-test
  (:require [clojure.test :as t]
            [aoc23.day20 :as day20]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 32000000 (day20/part1 (c/get-input "example.txt")))))
  (t/testing "example2" (t/is (= 11687500 (day20/part1 (c/get-input "example2.txt")))))
  (t/testing "input" (t/is (= 787056720 (day20/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= 212986464842911 (day20/part2 (c/get-input "input.txt"))))))

