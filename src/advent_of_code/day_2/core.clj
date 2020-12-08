(ns advent-of-code.day-2.core
  (:require [clojure.string :as str]))


(def input-path "./src/advent_of_code/day_2/input.txt")

(defn get-data [path]
  (->> (slurp path)
       str/split-lines
       (map #(str/replace % ":" ""))
       (map #(str/split % #" "))))


(defn valid-pass? [line]
  (let [[range char password] line
        [from* to*] (str/split range #"-")
        from (Integer/parseInt from*)
        to (Integer/parseInt to*)
        entries-count (->> (str/split password #"")
                           (filter #(= char %))
                           count)]
    (<= from entries-count to)))


(defn solution [data]
  (count (filter valid-pass? data)))

(comment
  (solution (get-data input-path))
;; => 550
)
