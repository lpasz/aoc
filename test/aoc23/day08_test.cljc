(ns aoc23.day08-test
  (:require [clojure.test :as t]
            [aoc23.day08 :as day08]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 2 (day08/part1 (c/get-input "example.txt")))))
  (t/testing "example2" (t/is (= 6 (day08/part1 (c/get-input "example2.txt")))))
  (t/testing "input" (t/is (= 17621 (day08/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 6 (day08/part2 (c/get-input "example3.txt")))))
  (t/testing "input" (t/is (= 20685524831999  (day08/part2 (c/get-input "input.txt"))))))

