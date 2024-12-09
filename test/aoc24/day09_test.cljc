(ns aoc24.day09-test
  (:require [clojure.test :as t]
            [aoc24.day09 :as day09]))

(t/deftest part1
  (t/testing "input" (t/is (= (day09/part1 "./assets/day09/input.txt") 259)))
  (t/testing "example" (t/is (= (day09/part1 "./assets/day09/example.txt") 14))))

;;(t/deftest part2
;;  (t/testing "input" (t/is (= (day09/part2 "./assets/day09/input.txt") 927)))
;;  (t/testing "example" (t/is (= (day09/part2 "./assets/day09/example.txt") 34))))

