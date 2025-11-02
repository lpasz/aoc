(ns aoc15.day19-test
  (:require [clojure.test :as t]
            [aoc15.day19 :as day19]))

(t/deftest part1
  (t/testing "example"
    (t/is (= 4 (day19/part1 "example.txt"))))
  (t/testing "part1"
    (t/is (=  535 (day19/part1 "input.txt")))))

(t/deftest part2
  (t/testing "example"
    (t/is (= 6 (time (day19/part2 "example2.txt")))))
  (t/testing "part2"
    (t/is (= 212 (day19/part2 "input.txt")))))
