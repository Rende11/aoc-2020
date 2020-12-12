(ns advent-of-code.day-3.core
  (:require [clojure.java.io :as io]))

(def input-path "./src/advent_of_code/day_3/input.txt")
(def test-input-path "./src/advent_of_code/day_3/test.txt")

(defn get-data [path]
  (with-open [rdr (io/reader path)]
    (doall
     (line-seq rdr))))

(defn build-map [map-part]
  (let [lines-count (count map-part)
        lines-width (count (first map-part))
        min-width (* lines-count 3)
        fragments-count (int (Math/ceil (/  min-width lines-width)))]
    (apply (partial map str) (repeat fragments-count map-part))))

(defn trace-coords [full-map]
  (let [lines-count (count full-map)]
    (->> [0 0]
         (iterate (fn [[a b]]
                    [(+ a 3) (+ b 1)]))
         (take-while (fn [[_a b]]
                       (<= b lines-count))))))

(defn acc-path [full-map coords]
  (map (fn [[a b]]
         (get-in full-map [b a])) coords))

(defn solution [data]
  (let [full-map (build-map data)
        coords (trace-coords full-map)
        path (acc-path (vec full-map) coords)]
    (count (filter #(= \# %) path))))


(comment
  (solution (get-data input-path))
;; => 265
  )
