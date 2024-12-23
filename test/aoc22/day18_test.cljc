(ns aoc22.day18-test
  (:require [clojure.test :as t]
            [aoc22.day18 :as day18]))

(t/deftest part1
  (t/testing "example" (t/is (= 64 (day18/unconnected-sides day18/ex-cubes))))
  (t/testing "input" (t/is (= 3494 (day18/unconnected-sides day18/cubes)))))

(t/deftest part2
  (t/testing "example" (t/is (= 58 (day18/exterior day18/ex-cubes))))
  (t/testing "input" (t/is (= 2062 (day18/exterior  day18/cubes)))))

