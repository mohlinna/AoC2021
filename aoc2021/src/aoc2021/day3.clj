(ns aoc2021.day3
  (:require [clojure.string :as str])
  (:gen-class))
  
(use 'clojure.java.io)

(defn get-lines [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))
    
(defn parse-int [s] (Integer/parseInt s))

(defn get-lines-as-ints [fname] (map parse-int (get-lines fname)))

(def input (get-lines "src/aoc2021/input3.txt"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
"Part 1"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn add-to-tally
  [tally s]
  )

(defn calc-gamma-epsilon
  [bitwise-tally n gamma epsilon]
  (if (= 0 (count bitwise-tally))
     (vector gamma epsilon)
     (if (> (first bitwise-tally) (/ n 2))
        (calc-gamma-epsilon (rest bitwise-tally) n (+ 1 (* 2 gamma)) (* 2 epsilon))
        (calc-gamma-epsilon (rest bitwise-tally) n (* 2 gamma) (+ 1 (* 2 epsilon))))))

(defn read-diagnostic-report
  [report bitwise-tally n]
  (if (= 0 (count report))
    (reduce * (calc-gamma-epsilon bitwise-tally n 0 0))
    (let [new-bits (map #(Character/getNumericValue %) (first report))]
      (read-diagnostic-report (rest report) (map + bitwise-tally new-bits) (+ 1 n) ))))
  
(defn part1 [] (println 
                (read-diagnostic-report input (replicate (count (first input)) 0) 0)))


