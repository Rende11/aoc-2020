(ns advent-of-code.day-6.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :refer [intersection]]))


(def input-path "./src/advent_of_code/day_6/input.txt")

(defn get-data [path]
  (-> path
      slurp
      (str/split #"\n\n")))

(defn solution [data]
  (->> data
       (map #(str/replace % "\n" ""))
       (map (comp count distinct))
       (reduce + 0)))


(comment
  (solution (get-data input-path))
  ;; => 6532
  )


(defn solution-2 [data]
  (->> data
       (map #(str/split % #"\n"))
       (map #(map set %))
       (map #(apply intersection %))
       (map count)
       (reduce + 0)))

(comment
  (solution-2 (get-data input-path))
  ;; => 3427
 )
