(ns aoc24.day16-test
  (:require [clojure.test :as t]
            [aoc24.day16 :as day16]))

(t/deftest part1
  (t/testing "input" (t/is (= (day16/part1 "input.txt") 107512)))
  (t/testing "example" (t/is (= (day16/part1 "example.txt") 7036)))
  (t/testing "example" (t/is (= (day16/part1 "example2.txt") 11048))))

(t/deftest part2
  (t/testing "input" (t/is (= (day16/part2 "input.txt") 561)))
  (t/testing "example" (t/is (= (day16/part2 "example.txt") 45)))
  (t/testing "example" (t/is (= (day16/part2 "example2.txt") 64))))
 
