(ns bowling-challenge.core
  (:gen-class)
  (require [clojure.core :refer :all]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))


(defn new-empty-frame
  "Takes in the number of the frame and return an empty frame with no scores."
  [n]
  {:frame        n
   :ball-score  []
   :frame-score 0})


(defn new-score-sheet
  "Create a new blank score sheet in the form of a list of maps.
  Each map represents a frame and consists of a frame number, result of two bowls and the score of the frame"
  []
  (loop [n   0
         acc []]
    (if (= n 11)
      acc
      (recur (inc n)
             (conj acc
                   (new-empty-frame n))))))


(defn strike-or-spare
  "Given the number of 2 bowls in a frame as `pins-down`, return the result of frame.
  The `pins-down` is a list given in the form [int int]."
  [pins-down]
  (cond (= (first pins-down) 10)
        :strike
        (= (+ (first pins-down) (second pins-down)) 10)
        :spare
        :else
        :open))


(defn no-of-frames-needed
  "Takes in a single `frame` and figures out how many frames are needed to figure out the score."
  [frame]
  (let [current-frame-state (strike-or-spare (:ball-score frame))
        frame-number (:frame frame)]
    (cond (= current-frame-state :open) 1
          (or (= current-frame-state :spare)
              (= frame-number 9)) 2
          (= current-frame-state :strike) 3)))


(defn pick-frames-to-use
  "This is used for determining which of the 3 frames are used for a strike.
  Only 3 frames are needed if there are 2 strikes in a row."
  [frames-needed]
  (if (= 3 (count frames-needed))
    (if (not= :strike (strike-or-spare (:ball-score (second frames-needed))))
      (take 2 frames-needed)
      frames-needed)
    frames-needed))


(defn score-frame
  "Given the frames needed to determine a sc ore for the frame, calculate the score of a frame.
  A list of maps is used as `frames-needed`.
  Also take into account the bonus bowls from strike and spare in the 10th frame"
  [frames-needed]
  (let [list-of-scores (->>  frames-needed
                             (map :ball-score)
                             (reduce concat))]
    (if (or (= :spare (strike-or-spare (:ball-score (first frames-needed)))) ;; HACK? : Used to calculate spare or 10th frame.
            (and (= 9 (:frame (second frames-needed)))
                 (= :strike (strike-or-spare (:ball-score (second frames-needed))))))
      (->> list-of-scores
           drop-last
           (reduce +))
      (reduce + list-of-scores))))


(defn assoc-frame-score!
  "Assoc the score of a frame in a list of maps.
  Takes in args `score-card` and `frame-no` in the form [atom int]"
  [score-card frame-no]
  (let [coll       (nthrest @score-card frame-no)
        value      (-> (first coll)
                       no-of-frames-needed
                       (take coll)
                       pick-frames-to-use
                       score-frame)]
    (swap! score-card update-in [frame-no] assoc :frame-score value)))


(defn traverse-board
  "Traverse a list of frames and assoc the resulting score of the frame."
  [score-card]
  (loop [n @score-card]
    (if (empty? (:ball-score (second n)))
      score-card
      (recur (do (assoc-frame-score! score-card (:frame (first n)))
                 (rest n)))))) ;; Can also be done using doseq on the list.


(defn total-score
  "Returns the score of a whole game."
  [score-card]
  (->> @score-card
       drop-last  ;;drop-last to remove final bonus frame for strike and spare
       (map :frame-score)
       (reduce +)))


(defn count-frames-used
  "Count number of frames played.
  Given a list of maps, determine how many frames have been played by looking at the score or bowls.
  Blank scores indicate a record has not been recorded while 0 indicates a gutter ball,"
  [score-card]
  (when ((complement empty?) (:ball-score (first @score-card)))
    (loop [n     @score-card
           count 0]
      (if (empty? (:ball-score (first n)))
        count
        (recur (rest n) (inc count))))))


(defn finish? ;; Maybe not use '?' in function name as it is not really a predicate.
  "Check if a game is finished.
  Return score of the game if it is finished or return false if game is incomplete."
  [score-card]
  (if (>= (count-frames-used score-card) 10)
    (let [bonus-bowls   (count (:ball-score (last @score-card)))
          final-frame-status (strike-or-spare (:ball-score(nth @score-card 9)))]
      (if (or (= :open final-frame-status)
              (and (or (= :spare final-frame-status)
                       (= :strike final-frame-status))
                   (= 2 bonus-bowls)))
        (total-score score-card)
        false))
    false))


:end-bowling-core
