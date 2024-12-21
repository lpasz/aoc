(ns aoc24.day21
  (:require [core :as c]))

(def numpad {\0 [[\2 \^] [\A \>]],
             \1 [[\2 \>] [\4 \^]],
             \2 [[\0 \v] [\1 \<], [\3 \>], [\5 \^]]
             \3 [[\2 \<] [\6 \^], [\A \v]],
             \4 [[\1 \v] [\5 \>], [\7 \^]],
             \5 [[\2 \v] [\4 \<], [\6 \>], [\8 \^]]
             \6 [[\3 \v] [\5 \<], [\9 \^]],
             \7 [[\4 \v] [\8 \>]],
             \8 [[\5 \v] [\7 \<], [\9 \>]]
             \9 [[\6 \v] [\8 \<]]
             \A [[\0 \<] [\3 \^]]})

(def dirpad {\^ [[\A \>], [\v \v]],
             \< [[\v \>]],
             \v [[\< \<], [\^ \^], [\> \>]],
             \> [[\v \<], [\A \^]]
             \A [[\^ \<], [\> \v]]})

(defn get-robot-pad [robot]
  (get {0 numpad} robot dirpad))

(defn dfs [coll level robot res])
(let [pad (get-robot-pad robot) ]
  (->> (concat \A coll)
       (partition 2))))


