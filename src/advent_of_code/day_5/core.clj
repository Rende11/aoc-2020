(ns advent-of-code.day-5.core
  (:require [clojure.java.io :as io]))


(def input-path "./src/advent_of_code/day_5/input.txt")

(defn get-data [path]
  (with-open [rdr (io/reader path)]
    (doall
     (line-seq rdr))))


(defn get-lower-part [coll]
  (let [lgt (count coll)]
    (take (/ lgt 2) coll)))

(defn get-upper-part [coll]
  (let [lgt (count coll)]
    (drop (/ lgt 2) coll)))

(defn process-char [coll ch]
  (case ch
    (\F \L) (get-lower-part coll)
    (\B \R) (get-upper-part coll)))

(defn get-seat-id [pass]
  (let [row-code (subs pass 0 7)
        seat-code (subs pass 7)
        row (first (reduce process-char (range 128) row-code))
        seat (first (reduce process-char (range 8) seat-code))]
    (+ (* row 8) seat)))

(defn solution [data]
  (->> data
       (map get-seat-id)
       (sort >)
       first))


(defn ->bin [string]
  (apply str (map #(case %
                     (\F \L) 0
                     (\B \R) 1) string)))

(defn ->num [bin]
  (Integer/parseInt bin 2))

(defn get-seat-id-2 [pass]
  (let [row-code (subs pass 0 7)
        seat-code (subs pass 7)
        row  (-> row-code ->bin ->num)
        seat (-> seat-code ->bin ->num)]
    (+ (* row 8) seat)))

(defn solution* [data]
  (->> data
       (map get-seat-id-2)
       (sort >)
       first))



(defn solution-2 [data]
  (->> data
       (map get-seat-id-2)
       sort
       (partition 2 1)
       (filter (fn [[a b]]
                 (= (+ a 2) b)))
       ffirst
       inc))

(comment
  (solution (get-data input-path))
;; => 991
   (solution* (get-data input-path)) ;; this one faster
   ;;
   (solution-2 (get-data input-path))
;; => 534
  )
