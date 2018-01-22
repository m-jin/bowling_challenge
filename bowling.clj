(ns bowling.core
  (require [clojure.core :refer :all]))

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
    (if (= :spare (strike-or-spare (:ball-score (first frames_needed))))
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
    (if (empty? (first n))
      score_card
      (recur (do (println "frame needed " (no-of-frames-needed (first n)))
                 (add-frame-score! score_card (:frame (first n)))
                 (rest n))))))


(defn finish? [score_card]
  (let [last_result   (strike-or-spare (:ball-score (last @score_card)))
        frames_played (count @score_card)]
    (if (or (= 11 frames_played)
            (and (nil? last_result)
               (= frames_played 10)))
          (reduce + (map :frame-score @score_card))
          false)))

:end_bowling_core
