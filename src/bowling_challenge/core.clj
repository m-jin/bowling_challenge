(ns bowling-challenge.core
  (:gen-class)
  (require [clojure.core :refer :all]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn new-score-sheet []
  (loop [n   0
         acc []]
    (if (= n 11)
      acc
      (recur (inc n)
             (conj acc
                   (let [rand1 (rand-int 11)
                         rand2 (rand-int (- 11 rand1))]
                     {:frame      n
                      :ball-score [10 0]
                      :frame-score 0}))))))


(defn strike-or-spare [pins_down]
  (cond (= (first pins_down) 10)
        :strike
        (= (+ (first pins_down) (second pins_down)) 10)
        :spare
        :else
        :open))


(defn no-of-frames-needed [frame]
  (let [current_frame_state (strike-or-spare (:ball-score frame))
        frame-number (:frame frame)]
    (cond (= current_frame_state :open)
          1
          (or (= current_frame_state :spare)
              (= frame-number 9))
          2
          (= current_frame_state :strike)
          3)))


(defn pick-frames-to-use [frames_needed]
  (if (= 3 (count frames_needed))
    (if (not= :strike (strike-or-spare (:ball-score (second frames_needed))))
      (take 2 frames_needed)
      frames_needed)
    frames_needed))


(defn score-frame [frames_needed]
  (let [list_of_scores (->>  frames_needed
                             (map :ball-score)
                             (reduce concat))]
    (if (or (= :spare (strike-or-spare (:ball-score (first frames_needed))))
            (and (= 9 (:frame (second frames_needed)))
                 (= :strike (strike-or-spare (:ball-score (second frames_needed)))))) ;;NOT HAPPY WITH THIS!
      (->> list_of_scores
           drop-last
           (reduce +))
      (reduce + list_of_scores))))


(defn add-frame-score! [score_card frame_no]
  (let [coll       (nthrest @score_card frame_no)
        value      (-> (first coll)
                       no-of-frames-needed
                       (take coll)
                       pick-frames-to-use
                       score-frame)]
    (swap! score_card update-in [frame_no] assoc :frame-score value)))


(defn traverse-board [score_card]
  (loop [n @score_card]
    (if (empty? (:ball-score (second n)))
      score_card
      (recur (do (add-frame-score! score_card (:frame (first n)))
                 (rest n))))))

(defn total [score_card]
  (reduce + (map :frame-score @score_card)))


(defn finish? [score_card]
  (let [bonus_bowls   (count (:ball-score (last @score_card)))
        final_frame_status (strike-or-spare (:ball-score(nth @score_card 9)))]
    (if (or (= :open final_frame_status)
            (and (or (= :spare final_frame_status)
                     (= :strike final_frame_status))
                 (= 2 bonus_bowls)))
      (total score_card)
      false)))



:end_bowling_core
0
