(ns aoc22.day05-test
  (:require [clojure.test :as t]
            [aoc22.day05 :as day05]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= "CMZ" (day05/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= "PSNRGBTFT" (day05/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= "MCD" (day05/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= "BNTZFPMMW" (day05/part2 (c/get-input "input.txt"))))))

