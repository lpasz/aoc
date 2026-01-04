(ns aoc25.day06-test
  (:require [clojure.test :as t]
            [aoc25.day06 :as day06]))

(t/deftest part1
  (t/testing "Example Input"
    (t/is (= 4277556 (day06/part1 "example.txt"))))
  (t/testing "Real Input"
    (t/is (= 6417439773370 (day06/part1 "input.txt")))))

(t/deftest part2
  (t/testing "Example Input"
    (t/is (= :boom (day06/part2 "example.txt")))))
;   (t/testing "Real Input"
;     (t/is (= :boom (day06/part2 "input.txt")))))
