(ns aoc24.day07-test
  (:require [clojure.test :as t]
            [aoc24.day07 :as day07]))

(t/deftest part1
  (t/testing "input" (t/is (= (day07/part1 "./assets/day07/input.txt") 945512582195)))
  (t/testing "example" (t/is (= (day07/part1 "./assets/day07/example.txt") 3749))))

(t/deftest part2
  ;; (t/testing "input" (t/is (= (day07/part2 "./assets/day07/input.txt") 271691107779347)))
  (t/testing "example" (t/is (= (day07/part2 "./assets/day07/example.txt") 11387))))

