(ns aoc2021.core
  (:require [aoc2021.day1 :as day1])
  (:require [aoc2021.day2 :as day2])
  (:gen-class))

(defn -main
  "Advent of Code 2021"
  [& args]
  (println "==============" "AOC2021 Day 1" "==============")
  ;(time (day1/part1))
  ;(time (day1/part2))
  (println "==============" "AOC2021 Day 2" "==============")
  ;(time (day2/part1))
  (time (day2/part2))
  (println))
  
