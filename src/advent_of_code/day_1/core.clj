(ns advent-of-code.day-1
  (:require [clojure.string :as str]))

(def input-path "./src/advent_of_code/day_1/input.txt")

(defn get-nums [path]
  (let [raw-nums (-> (slurp path)
                     (str/split #"\n"))]
    (map #(Integer/parseInt %) raw-nums)))

(defn solution [numbers]
  (loop [nums numbers]
    (let [n (first nums)
          sums (map #(hash-map :sum (+ n %) :term %) nums)]
      (if-let [result (first (filter #(= 2020 (:sum %)) sums))]
        (* n (:term result))
        (recur (rest nums))))))

(comment
  (solution (get-nums input-path))
  ;; => 731731)
