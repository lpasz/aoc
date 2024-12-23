(ns aoc22.day15-test
  (:require [clojure.test :as t]
            [aoc22.day15 :as day15]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 26 (day15/part1 (c/get-input "example.txt") 10))))
  (t/testing "input" (t/is (= 5525847 (day15/part1 (c/get-input "input.txt") 2000000)))))

(t/deftest part2
  (t/testing "example" (t/is (= 56000011 (day15/part2 (c/get-input "example.txt") 20))))
  (t/testing "input" (t/is (= 13340867187704 (day15/part2 (c/get-input "input.txt") 4000000)))))


