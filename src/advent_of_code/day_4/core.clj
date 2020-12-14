(ns advent-of-code.day-4.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.walk :as walk]
            [clojure.spec.alpha :as s]))


(def input-path "./src/advent_of_code/day_4/input.txt")

(defn parse-case [c]
  (->>
   (str/split c #"\s")
   (mapv (fn [v]
           (str/split v #":")))
   (into {})
   walk/keywordize-keys))

(defn get-data [path]
  (let [str-data (slurp path)
        cases (str/split str-data #"\n\n")]
    (map parse-case cases)))

(defn valid-passport? [{:keys [byr iyr eyr hgt hcl ecl pid]}]
  (and byr iyr eyr hgt hcl ecl pid))

(defn solution [passports]
  (count (filter valid-passport? passports)))

(comment
  (solution (get-data input-path))
;; => 230

  )


(defn valid-passport-extra? [{:keys [byr iyr eyr hgt hcl ecl pid]}]
  (and byr iyr eyr hgt hcl ecl pid
       (<= 1920 (Integer/parseInt byr) 2002)
       (<= 2010 (Integer/parseInt iyr) 2020)
       (<= 2020 (Integer/parseInt eyr) 2030)
       (let [[_ num unit] (re-matches #"(\d+)(cm|in)" hgt)]
             (case unit
               "cm" (<= 150 (Integer/parseInt num) 193)
               "in" (<= 59 (Integer/parseInt num) 76)
               false))
       (re-matches #"#[0-9a-f]{6}" hcl)
       (#{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} ecl)
       (re-matches #"\d{9}" pid)))


(defn solution-2 [passports]
  (count (filter valid-passport-extra? passports)))

(comment
  (solution-2 (get-data input-path))
;; => 156

  )


(s/def ::->int
  (s/conformer
    (fn [string]
      (Integer/parseInt string))))

(s/def :passport/byr
  (s/and
   ::->int
   #(<= 1920 % 2002)))

(s/def :passport/iyr
  (s/and
   ::->int
   #(<= 2010 % 2020)))

(s/def :passport/eyr
  (s/and
   ::->int
   #(<= 2020 % 2030)))

(s/def ::get-num
  (s/conformer
   (fn [string]
     (re-find #"^\d+" string))))

(s/def ::->cm
  (s/and
   (partial re-find #"\d+cm")
   ::get-num
   ::->int
   #(<= 150 % 193)))

(s/def ::->in
  (s/and
   (partial re-find #"\d+in")
   ::get-num
   ::->int
   #(<= 59 % 76)))

(s/def :passport/hgt
  (s/or :cm ::->cm :in ::->in))

(s/def :passport/hcl
  (partial re-matches #"#[0-9a-f]{6}"))

(s/def :passport/ecl
  #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"})

(s/def :passport/pid
  (partial re-matches #"\d{9}"))

(s/def ::passport
  (s/keys :req-un [:passport/byr
                   :passport/iyr
                   :passport/eyr
                   :passport/hgt
                   :passport/hcl
                   :passport/ecl
                   :passport/pid]))

(defn solution-3 [data]
  (->> data
       (filter #(s/valid? ::passport %))
       count))

(comment
  (solution-3 (get-data input-path))
  )
