(ns aoc22.day10-test
  (:require [clojure.test :as t]
            [aoc22.day10 :as day10]
            [core :as c]))

(t/deftest part1
  (t/testing "input" (t/is (= 13440 (day10/part1 (c/get-input "input.txt"))))))

(t/deftest part2
  (t/testing "input" (t/is (= (str "###  ###  ####  ##  ###   ##  ####  ##  \n"
                                   "#  # #  #    # #  # #  # #  #    # #  # \n"
                                   "#  # ###    #  #    #  # #  #   #  #  # \n"
                                   "###  #  #  #   # ## ###  ####  #   #### \n"
                                   "#    #  # #    #  # # #  #  # #    #  # \n"
                                   "#    ###  ####  ### #  # #  # #### #  # ")
                              (day10/part2 (c/get-input "input.txt"))))))


