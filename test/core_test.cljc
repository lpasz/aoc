(ns core-test
  (:require [clojure.test :as t]
            [core :as c]))

(t/deftest  bron-kerbosch
  (t/testing "bk"
    (t/is (= (c/bron-kerbosch #{1 2 3 4 5 6}
                              {1 #{2 5}
                               2 #{1 5 3}
                               5 #{2 1 4}
                               3 #{2 4}
                               4 #{5 3 6}
                               6 #{4}})
             [#{1 2 5} #{4 6} #{4 3} #{4 5} #{3 2}]))))

