(ns aoc15.day22-test
  (:require [clojure.test :as t]
            [aoc15.day22 :as day22]))

; (t/deftest part1
;   (t/testing "part1"
;     (t/is (= :boom (day22/part1 "input.txt")))))

; (t/deftest part2
;   (t/testing "part2"
;     (t/is (= :boom (day22/part2 "input.txt")))))

(t/deftest player-round
  (t/testing "part2"
    (t/is (= {:me {:hp 10, :armor 0, :mana 77, :consumed-mana 173},
              :boss {:hp 13, :dmg 8},
              :effects [{:name :poison, :lasts 6, :type :every-turn, :dmg 3}]}
             (day22/player-round {:me {:hp 10 :armor 0 :mana 250 :consumed-mana 0}
                                  :boss {:hp 13 :dmg 8}
                                  :effects []}
                                 (:poison day22/magics))))))
