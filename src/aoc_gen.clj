(ns aoc-gen
  (:require [clojure.java.io :as io]
            [org.httpkit.client :as http-client]
            [clojure.string :as str])
  (:gen-class))

(defn- clj-file [y-suffix day-pad]
  (str "(ns aoc" y-suffix ".day" day-pad "
  (:require [core :as c]
            [clojure.string :as str]))

(defn part1 [file-path] :part1)
(defn part2 [file-path] :part2)"))

(defn- cljc-file [y-suffix day-pad]
  (str "(ns aoc" y-suffix ".day" day-pad "-test
  (:require [clojure.test :as t]
            [aoc" y-suffix ".day" day-pad " :as day" day-pad "]))

(t/deftest part1
  (t/testing \"Example Input\"
    (t/is (= :boom (day" day-pad "/part1 \"example.txt\"))))
  (t/testing \"Real Input\"
    (t/is (= :boom (day" day-pad "/part1 \"input.txt\")))))

(t/deftest part2
  (t/testing \"Example Input\"
    (t/is (= :boom (day" day-pad "/part2 \"example.txt\"))))
  (t/testing \"Real Input\"
    (t/is (= :boom (day" day-pad "/part2 \"input.txt\")))))"))

(defn- format-aoc-inputs [year day]
  (let [y (if (string? year) (Long/parseLong year) year)
        d (if (string? day) (Long/parseLong day) day)
        y-suffix-long (mod y 100)
        y-suffix (format "%02d" y-suffix-long)
        day-pad (format "%02d" d)]
    {:year-full y :y-suffix y-suffix :day-pad day-pad}))

(defn- create-file [filepath content]
  (let [f (io/file filepath)]
    (io/make-parents f)
    (spit f content)
    (str "Criado: " filepath)))

(defn get-input-from-aoc [year day]
  (-> (str "https://adventofcode.com/20" year "/day/" (parse-long day) "/input")
      (http-client/get {:headers {"Cookie" (str/trim (slurp ".aoc-session"))}})
      (deref)))

(defn- setup-day [year day]
  (let [{:keys [y-suffix day-pad]} (format-aoc-inputs year day)
        mod-path (str "src/aoc" y-suffix "/day" day-pad ".clj")
        test-path (str "test/aoc" y-suffix "/day" day-pad "_test.clj")
        asset-dir (str "assets/aoc" y-suffix "/day" day-pad "/")
        input-path (str asset-dir "input.txt")
        example-path (str asset-dir "example.txt")]
    (println "--- Gerando Arquivos ---")
    (println (create-file mod-path (clj-file y-suffix day-pad)))
    (println (create-file test-path (cljc-file y-suffix day-pad)))
    (io/make-parents input-path)
    (let [response (get-input-from-aoc year day)]
      (when (= 200 (:status response))
        (println (str "Criado: " input-path))
        (spit input-path (:body response))))
    (spit example-path "")
    (println (str "Criado: " example-path))
    (println (str "\n✨ Day " day-pad " (20" year ") pronto!")))

  (shutdown-agents))

(defn -main
  "Função principal para ser executada via CLI.
  Uso: clj -A:gen <YEAR> <DAY>"
  [& args]
  (let [[year-arg day-arg & _] args]
    (cond
      (and year-arg day-arg)
      (setup-day year-arg day-arg)

      :else
      (println "Uso: clj -A:gen <YEAR> <DAY>\nEx: clj -A:gen 2024 4 ou clj -A:gen 24 \"04\""))))
