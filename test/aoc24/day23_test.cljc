(ns aoc24.day23-test
  (:require [clojure.test :as t]
            [aoc24.day23 :as day23]))

(t/deftest part1
  (t/testing "input" (t/is (= (day23/part1 "input.txt") 1352)))
  (t/testing "example" (t/is (= (day23/part1 "example.txt") 7))))

(t/deftest part2
  (t/testing "input" (t/is (= (day23/part2 "input.txt") "dm,do,fr,gf,gh,gy,iq,jb,kt,on,rg,xf,ze")))
  (t/testing "example" (t/is (= (day23/part2 "example.txt") "co,de,ka,ta"))))

