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

;; Part 2
(defn valid-pass-strict? [line]
  (let [[range char password] line
        [pos1* pos2*] (str/split range #"-")
        pos1 (dec (Integer/parseInt pos1*))
        pos2 (dec (Integer/parseInt pos2*))
        splitted  (str/split password #"")
        char1 (nth splitted pos1)
        char2 (nth splitted pos2)]
    (and
     (not= char1 char2)
     (or
      (= char char1)
      (= char char2)))))

(filter #(= % \l) "hello")


(defn solution-2 [data]
  (count (filter valid-pass-strict? data)))

(comment
  (solution-2 (get-data input-path))
;; => 634
  )

(comment
  ;; Tips from Fred Overflow solution
  ;; 1. Use re-matches to parse one line
  (re-matches #"(\d+)-(\d+) (\w:) (\w+)" "1-3 a: abcde")
;; => ["1-3 a: abcde" "1" "3" "a:" "abcde"]

;;   2. Use lazy file reader func "with-open"
;;   3. You can filter chars from string do not need to split it
  (filterv #(= % \l) "hello")
;;   4. Exclusive or
  ;; (not=
  ;;  (= a b)
  ;;  (= a c))

  )
