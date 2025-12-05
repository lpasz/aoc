(ns aoc-gen
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

;; --- Templates de Arquivo (Corrigidos conforme sua especificação) ---

(defn- clj-file [y-suffix day-pad] ; Usando y-suffix e day-pad internamente
  (str "(ns aoc" y-suffix ".day" day-pad "
  (:require [core :as c]
            [clojure.string :as str]))

(defn part1 [file-path] :part1)
(defn part2 [file-path] :part2)"))

(defn- cljc-file [y-suffix day-pad] ; Usando y-suffix e day-pad internamente
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

;; --- Lógica de Formatação e Geração ---

(defn- format-aoc-inputs [year day]
  "Converte entrada flexível (string/número, 2025/25, 4/04) em strings de 2 dígitos."
  (let [y (if (string? year) (Long/parseLong year) year)
        d (if (string? day) (Long/parseLong day) day)
        
        ;; Pega o sufixo de 2 dígitos do ano (ex: 2025 -> "25", 25 -> "25")
        y-suffix-long (mod y 100)
        y-suffix (format "%02d" y-suffix-long)
        
        ;; Garante 2 dígitos para o dia (ex: 4 -> "04")
        day-pad (format "%02d" d)]
    
    {:year-full y :y-suffix y-suffix :day-pad day-pad}))

(defn- create-file [filepath content]
  (let [f (io/file filepath)]
    (io/make-parents f)
    (spit f content)
    (str "Criado: " filepath)))

(defn- setup-day [year day]
  (let [{:keys [y-suffix day-pad]} (format-aoc-inputs year day)
        
        mod-path (str "src/aoc" y-suffix "/day" day-pad ".clj")
        test-path (str "test/aoc" y-suffix "/day" day-pad "_test.clj")
        asset-dir (str "assets/aoc" y-suffix "/day" day-pad "/")
        input-path (str asset-dir "input.txt")
        example-path (str asset-dir "example.txt")]

    (println "--- Gerando Arquivos ---")
    ;; Chamando as funções de template renomeadas: clj-file e cljc-file
    (println (create-file mod-path (clj-file y-suffix day-pad)))
    (println (create-file test-path (cljc-file y-suffix day-pad)))

    ;; Garante que a pasta de assets exista e cria arquivos placeholders
    (io/make-parents input-path)
    (spit input-path "") ; Arquivo de entrada real vazio (pode ser preenchido manualmente)
    (spit example-path "") ; Arquivo de exemplo vazio
    (println (str "Criado assets/dir: " asset-dir))

    (println (str "\n✨ Day " day-pad " (" year ") pronto!")))
  
  (shutdown-agents))

;; --- Função Principal (Entry Point) ---

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
