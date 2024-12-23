(ns aoc22.day07-test
  (:require [clojure.test :as t]
            [aoc22.day07 :as day07]
            [core :as c]))

(t/deftest part1
  (t/testing "example" (t/is (= 95437 (day07/part1 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 1306611 (day07/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "example" (t/is (= 24933642 (day07/part2 (c/get-input "example.txt")))))
  (t/testing "input" (t/is (= 13210366 (day07/part2 (c/get-input "input.txt"))))))


