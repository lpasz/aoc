(ns aoc25.day02-test
  (:require [clojure.test :as t]
            [aoc25.day02 :as day02]))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 1227775554 (day02/part1 "example.txt")))
    (t/is (= 20223751480 (day02/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 4174379265 (day02/part2 "example.txt")))
    (t/is (= 30260171216 (day02/part2 "input.txt")))))
