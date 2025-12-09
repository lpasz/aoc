(ns aoc25.day08
  (:require [core :as c]))

(defn parse [file-path]
  (->> (c/get-input file-path)
       (c/extract-numbers)
       (partition 3)))

(defn distances [points]
  (loop [[point1 & rest-points] points
         acc {}]
    (if (nil? rest-points)
      acc
      (recur rest-points
             (reduce (fn [acc point2]
                       (assoc acc
                              [point1 point2]
                              (c/euclidean-distance-3d point1 point2)))
                     acc
                     rest-points)))))

(defn connect-points [p1 p2 connections]
  (loop [connections connections
         curr-conn #{}
         conns []]
    (if (empty? connections)
      (if (not-empty curr-conn)
        (conj conns curr-conn)
        (conj conns #{p1 p2}))
      (let [[conn & rest-conn] connections]
        (if (or (conn p1) (conn p2))
          (recur rest-conn
                 (into conn (conj curr-conn p1 p2))
                 conns)
          (recur rest-conn
                 curr-conn
                 (conj conns conn)))))))

(defn connect [points]
  (loop [[[[p1 p2] _distance] & rest-points] points
         connections []]
    (let [connections (connect-points p1 p2 connections)]
      (if (empty? rest-points)
        connections
        (recur rest-points connections)))))

(defn connect-until-all-connected [cnt points]
  (loop [[[[p1 p2] _distance] & rest-points] points
         connections []]
    (let [connections (connect-points p1 p2 connections)]
      (if (and (c/one? (count connections))
               (= (count (first connections)) cnt))
        (* (first p1) (first p2))
        (if (empty? rest-points)
          connections
          (recur rest-points connections))))))

(defn part1
  ([file-path] (part1 file-path 1000))
  ([file-path n]
   (let [points (parse file-path)
         distances (distances points)]
     (->> distances
          (sort-by second <)
          (take n)
          (connect)
          (map count)
          (sort >)
          (take 3)
          (c/product)))))

(defn part2 [file-path]
  (let [points (parse file-path)
        cnt (count points)
        distances (distances points)]
    (->> distances
         (sort-by second <)
         (connect-until-all-connected cnt))))

