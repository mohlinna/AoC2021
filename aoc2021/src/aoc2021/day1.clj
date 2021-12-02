(ns aoc2021.day1
  (:gen-class))
  
(use 'clojure.java.io)

(defn get-lines [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))
    
(defn parse-int [s] (Integer/parseInt s))

(defn get-lines-as-ints [fname] (map parse-int (get-lines fname)))

(def input (get-lines-as-ints "src/aoc2021/input1.txt"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
"Part 1"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn count-increases 
  [measurements]
  (if (= 1 (count measurements))
    0
    (if (< (first measurements) (second measurements))
    	(+ 1 (count-increases (rest measurements)))
    	(count-increases (rest measurements)))))
    	
(defn part1 [] (println (count-increases input)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
"Part 2"
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn count-increases-sum
  [prev-sum measurements]
  (if (< (count measurements) 3)
    0
    (let [new-sum (reduce + (take 3 measurements))]
      (if (< prev-sum new-sum)
    	(+ 1 (count-increases-sum new-sum (rest measurements)))
    	(count-increases-sum new-sum (rest measurements))))))
    	
(defn part2 [] (println (count-increases-sum (reduce + (take 3 input)) (rest input))))

