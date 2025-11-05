(ns aoc15.day21-test
  (:require [clojure.test :as t]
            [aoc15.day21 :as day21]))


(def me {:hp 8 :dmg 5 :armor 5 :cost 0})
(def boss {:hp 12 :dmg 7 :armor 2})

(t/deftest battle
  (t/testing "battle"
    (t/is (= {:won 0} (day21/battle boss me )))))

(t/deftest part1
  (t/testing "part1"
    (t/is (= 91 (day21/part1 "input.txt")))))

(t/deftest part2
  (t/testing "part2"
    (t/is (= 158 (day21/part2 "input.txt")))))
