(ns advent-of-code.day-1
  (:require [clojure.string :as str]))

(def input-path "./src/advent_of_code/day_1/input.txt")

(defn get-nums [path]
  (->> (slurp path)
       str/split-lines
       (map #(Integer/parseInt %) )))

(defn solution [numbers]
  (-> (for [a numbers
            b numbers
            :when (= 2020 (+ a b))]
        (* a b))
      first))

(comment
  (solution (get-nums input-path))
  ;; => 731731
)

(defn solution-2 [numbers]
  (-> (for [a numbers
            b numbers
            c numbers
            :when (= 2020 (+ a b c))]
        (* a b c))
      first))

(comment
  (solution-2 (get-nums input-path)))
;; => 116115990
