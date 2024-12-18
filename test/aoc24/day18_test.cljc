(ns aoc24.day18-test
  (:require [clojure.test :as t]
            [aoc24.day18 :as day18]))

(t/deftest part1
  (t/testing "input" (t/is (= (day18/part1 "./assets/day18/input.txt" [70 70] 1024) 262)))
  (t/testing "example" (t/is (= (day18/part1 "./assets/day18/example.txt" [6 6] 12) 22))))

(t/deftest part2
  (t/testing "input" (t/is (= (day18/part2 "./assets/day18/input.txt" [70 70] 1024) '(22 20))))
  (t/testing "example" (t/is (= (day18/part2 "./assets/day18/example.txt" [6 6] 12) '(6 1)))))


