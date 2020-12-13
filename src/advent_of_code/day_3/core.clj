(ns advent-of-code.day-3.core
  (:require [clojure.java.io :as io]))

(def input-path "./src/advent_of_code/day_3/input.txt")
(def test-input-path "./src/advent_of_code/day_3/test.txt")

(defn get-data [path]
  (with-open [rdr (io/reader path)]
    (doall
     (line-seq rdr))))

(defn build-map [map-part mult]
  (let [lines-count (count map-part)
        lines-width (count (first map-part))
        min-width (* lines-count mult)
        fragments-count (int (Math/ceil (/  min-width lines-width)))]
    (apply (partial map str) (repeat fragments-count map-part))))

(defn trace-coords [full-map x-step y-step]
  (let [lines-count (count full-map)]
    (->> [0 0]
         (iterate (fn [[a b]]
                    [(+ a x-step) (+ b y-step)]))
         (take-while (fn [[_a b]]
                       (<= b lines-count))))))

(defn acc-path [full-map coords]
  (map (fn [[a b]]
         (get-in full-map [b a])) coords))

(defn solution [data x-step y-step]
  (let [full-map (build-map data x-step)
        coords   (trace-coords full-map x-step y-step)
        path     (acc-path (vec full-map) coords)]
    (count (filter #(= \# %) path))))


(comment
  (solution (get-data input-path) 3 1)
;; => 265
  )


(def steps
  [[1 1]
   [3 1]
   [5 1]
   [7 1]
   [1 2]])

(comment
;; => ;;part 2
  (->> steps
       (map #(apply (partial solution (get-data input-path)) %))
       (reduce *))
  ;; => 3154761400
  )
