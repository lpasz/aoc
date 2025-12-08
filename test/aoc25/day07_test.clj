(ns aoc25.day07-test
  (:require [clojure.test :as t]
            [aoc25.day07 :as day07]))

(t/deftest part1
  (t/testing "Example Input"
    (t/is (= 21 (day07/part1 "example.txt"))))
  (t/testing "Real Input"
    (t/is (= 1507 (day07/part1 "input.txt")))))

(t/deftest part2
  (t/testing "Example Input"
    (t/is (= 40 (day07/part2 "example.txt"))))
  (t/testing "Real Input"
    (t/is (= 1537373473728 (day07/part2 "input.txt")))))
