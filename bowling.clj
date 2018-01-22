(ns bowling.core
  (require [clojure.core :refer :all]))

(defn new-score-sheet []
  (loop [n   0
         acc []]
    (if (= n 11)
      acc
      (recur (inc n)
             (conj acc [0 0])))))


(defn strike-or-spare [pins_down]
  (cond (= (first pins_down) 10)
        :strike
        (= (+ (first pins_down) (second pins_down)) 10)
        :spare
        :else
        :open))


(defn score-frame
  ([frame_1]
   (+ (first frame_1) (second frame_1)))

  ([frame_1 frame_2]
   (let [frame_1_score  (reduce + frame_1)
         frame_1_result (strike-or-spare frame_1)

         frame_2_score (reduce + frame_2)]

     (cond (= frame_1_result :strike)
           (+ frame_1_score frame_2_score)
           (= frame_1_result :spare)
           (+ frame_1_score (first frame_2))
           :else
           frame_1_score))))

(defn traverse-score [score_card]
  (loop [n @score_card]
    (if (empty? n)
      score_card
      (recur (rest n)))))

:end_bowling_core
