(ns core-test
  (:require [clojure.test :as t]
            [core :as c]))

;; (matrix-values-to-graph (to-matrix "789\n456\n123\n#0A") #{\0 \1 \2 \3\ \4 \5 \6 \7 \8 \9 \A})

(comment
  (def graph
    "Map of node => set of adjacent nodes."
    {"A" #{"B", "E"}
     "B" #{"A", "C", "D"}
     "C" #{"B", "D"}
     "D" #{"B", "C", "E"}
     "E" #{"A", "D"}})

  (def costs
    "Map of node => adjacent node => cost. This could
  be replaced with any cost function of the shape
  (node, node') => cost."
    {"A" {"B" 2, "E" 10}
     "B" {"A" 2, "C" 3, "D" 4}
     "C" {"B" 3, "D" 2}
     "D" {"B" 4, "C" 3, "E" 10}
     "E" {"A" 10, "D" 10}})

  (a* graph "A" "D")
  (a* graph "A" "D" costs)
;;
  )

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

(t/deftest includes-count
  (t/testing "bk"
    (t/is (= (c/includes-count "123" "4") 0))
    (t/is (= (c/includes-count "123" "1") 1))
    (t/is (= (c/includes-count "113" "1") 2))
    (t/is (= (c/includes-count "123 123 123" "123") 3))))

(t/deftest replace-nth
  (t/testing "bk"
    (t/is (= (c/replace-nth "123" "4" "0" 0) "123"))
    (t/is (= (c/replace-nth "123" "1" "0" 0) "123"))
    (t/is (= (c/replace-nth "123" "1" "0" 1) "023"))
    (t/is (= (c/replace-nth "113" "1" "0" 2) "103"))
    (t/is (= (c/replace-nth "123 123 123" "123" "000" 3) "123 123 000"))))
