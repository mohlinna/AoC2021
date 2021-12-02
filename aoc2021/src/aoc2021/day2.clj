(ns aoc2021.day2
  (:require [clojure.string :as str])
  (:gen-class))
  
(use 'clojure.java.io)

(defn get-lines [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))
    
(defn parse-int [s] (Integer/parseInt s))

(defn get-lines-as-ints [fname] (map parse-int (get-lines fname)))

(def input (get-lines "src/aoc2021/input2.txt"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
"Part 1"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn navigate 
  [dirs hor ver]
  (if (= 0 (count dirs))
    (* hor ver)
    (let [dir (first dirs) first-letter (subs dir 0 1)]
      (if (= first-letter "f")
        (navigate (rest dirs) (+ hor (Integer/parseInt (subs dir 8))) ver)
        (if (= first-letter "d")
          (navigate (rest dirs) hor (+ ver (Integer/parseInt (subs dir 5))))
          (navigate (rest dirs) hor (- ver (Integer/parseInt (subs dir 3)))))))))
    
    	
(defn part1 [] (println (navigate input 0 0)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
"Part 2"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn navigate-aim
  [dirs hor ver aim]
  (if (= 0 (count dirs))
    (* hor ver)
    (let [dir (first dirs) first-letter (subs dir 0 1)]
      (if (= first-letter "f")
        (let [x (Integer/parseInt (subs dir 8))]
          (navigate-aim (rest dirs) (+ hor x) (+ ver (* aim x)) aim))
        (if (= first-letter "d")
          (navigate-aim (rest dirs) hor ver (+ aim (Integer/parseInt (subs dir 5))))
          (navigate-aim (rest dirs) hor ver (- aim (Integer/parseInt (subs dir 3)))))))))
          
(defn part2 [] (println (navigate-aim input 0 0 0)))

