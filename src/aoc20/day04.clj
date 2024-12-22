(ns aoc20.day04
  (:require [clojure.string :as str]))

(def example (slurp "src/advent-of-code-2020/day-04/day-04-ex.txt"))

(def input (slurp "src/advent-of-code-2020/day-04/day-04.txt"))

(defn passport-entry-text->map [passport-entry]
  (->> (str/split passport-entry #"(\s|:)")
       (partition 2)
       (map (fn [[key val]] [(keyword key) val]))
       (into {})))

(def req-keys #{:ecl :pid :eyr :hcl :byr :iyr :hgt})

(defn to-passport-map [text]
  (->> (str/split text #"\n\n")
       (map passport-entry-text->map)))

(defn valid-passport? [passport] (every? (set (keys passport)) req-keys))

(defn count-valid [text] (->> (to-passport-map text)
                              (filter valid-passport?)
                              (count)))

(= 254 (count-valid input))

(defn between? [n this that] (and (>= n this) (>= that n)))

(defn valid-byr? [passport]
  (some-> passport :byr (Integer/parseInt) (between? 1920 2002)))

(defn valid-iyr? [passport]
  (some-> passport :iyr (Integer/parseInt) (between? 2010 2020)))

(defn valid-eyr? [passport]
  (some-> passport :eyr (Integer/parseInt) (between? 2020 2030)))

(defn hgt? [passport measure] (str/ends-with? (:hgt passport) measure))

(defn ->int [hgt] (some->> hgt (re-find #"\d+") (read-string)))

(defn valid-hgt? [passport]
  (cond
    (hgt? passport "cm") (-> passport :hgt ->int (between? 150 193))
    (hgt? passport "in") (-> passport :hgt ->int (between? 59 76))
    :else false))

(defn valid-hcl? [passport]
  (some->>  passport :hcl (re-matches #"#([0-9]|[a-f]){6}")))

(defn valid-ecl? [passport]
  (some-> passport :ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}))

(defn valid-pid? [passport]
  (some->> passport :pid (re-matches #"[0-9]{9}")))

(defn full-valid-valid-passport? [passport]
  (and (valid-passport? passport)
       (valid-byr? passport)
       (valid-eyr? passport)
       (valid-hgt? passport)
       (valid-iyr? passport)
       (valid-hcl? passport)
       (valid-ecl? passport)
       (valid-pid? passport)))

(defn count-full-valid [text]
  (->> (to-passport-map text)
       (filter full-valid-valid-passport?)
       (count)))

(count-full-valid input)
