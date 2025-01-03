(ns aoc15.day04-test
  (:require [clojure.test :as t]
            [aoc15.day04 :as day04]))

(t/deftest part1
  (t/testing "input"
    (t/is (= 117946 (day04/part1 "ckczppom")))))

(t/deftest part2
  (t/testing "input"
    (t/is (= 3938038 (day04/part2 "ckczppom")))))
