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


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
"Part 2"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn binary-str-to-int
  ([s] (binary-str-to-int s 0))
  ([s n] 
   (if (= 0 (count s))
     n
     (binary-str-to-int (subs s 1) (+ (Integer/parseInt (subs s 0 1)) (* 2 n))))))

(defn outer-loop  [report i inner-loop]
    (if (= 1 (count report))
      (first report)
      (inner-loop report i '() '())))

(defn o2-gen-inner-loop
  [report i group0 group1]
  (if (= 0 (count report))
    (if (> (count group0) (count group1))
      (outer-loop group0 (+ 1 i) o2-gen-inner-loop)
      (outer-loop group1 (+ 1 i) o2-gen-inner-loop))
    (let [current (first report)]
      (if (= \0 (.charAt current i))
        (o2-gen-inner-loop (rest report) i (cons current group0) group1)
        (o2-gen-inner-loop (rest report) i group0 (cons current group1))))
    ))

(defn co2-scrub-inner-loop
  [report i group0 group1]
  (if (= 0 (count report))
    (if (> (count group0) (count group1))
      (outer-loop group1 (+ 1 i) co2-scrub-inner-loop)
      (outer-loop group0 (+ 1 i) co2-scrub-inner-loop))
    (let [current (first report)]
      (if (= \0 (.charAt current i))
        (co2-scrub-inner-loop (rest report) i (cons current group0) group1)
        (co2-scrub-inner-loop (rest report) i group0 (cons current group1))))
    ))

(defn o2-gen 
  [report]
  (binary-str-to-int (outer-loop report 0 o2-gen-inner-loop))
  )

(defn co2-scrubber 
  [report]
  (binary-str-to-int (outer-loop report 0 co2-scrub-inner-loop))
  )


(defn part2 [] (println
                (* (o2-gen input) (co2-scrubber input))))
