(ns bowling-challenge.core-test
  (:require [clojure.test :refer :all]
            [bowling-challenge.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(def strike-bowl [10 0])
(def spare-bowl [9 1])
(def open-bowl [0 9])

(deftest strike-or-spare-test
  (testing "Strike bowl"
    (is (= :strike
           (strike-or-spare strike-bowl))))
  (testing "Spare bowl"
    (is (= :spare
           (strike-or-spare spare-bowl))))
  (testing "Open bowl"
    (is (= :open
           (strike-or-spare open-bowl)))))

(def strike-frame {:frame 0, :ball-score [10 0], :frame-score 0})
(def spare-frame {:frame 0, :ball-score [1 9], :frame-score 0})
(def open-frame {:frame 0, :ball-score [1 0], :frame-score 0})

(deftest no-of-frames-needed-test
  (testing "Strike frame with no-of-frames-needed-test."
    (is (= 3
           (no-of-frames-needed strike-frame))))
  (testing "Spare frame with no-of-frames-needed-test."
    (is (= 2
           (no-of-frames-needed spare-frame))))
  (testing "Open frame with no-of-frames-needed-test."
    (is (= 1
           (no-of-frames-needed open-frame)))))

(def single-strike [{:frame 0, :ball-score [10 0], :frame-score 0}
                    {:frame 1, :ball-score [1 1], :frame-score 0}
                    {:frame 2, :ball-score [1 1], :frame-score 0}])

(def double-strike [{:frame 0, :ball-score [10 0], :frame-score 0}
                    {:frame 1, :ball-score [10 0], :frame-score 0}
                    {:frame 2, :ball-score [1 1], :frame-score 0}])

(def spare [{:frame 0, :ball-score [9 1], :frame-score 0}
            {:frame 1, :ball-score [1 1], :frame-score 0}])

(deftest pick-frames-to-use-test
  (testing "Pick frames of single strike."
    (is (= (take 2 single-strike)
           (pick-frames-to-use single-strike))))
  (testing "Pick frames of double strike."
    (is (= double-strike
           (pick-frames-to-use double-strike))))
  (testing "Pick frames of spare"
    (is (= spare
           (pick-frames-to-use spare)))))

(def all-strike (atom [{:frame 0, :ball-score [10 0], :frame-score 0}
                       {:frame 1, :ball-score [10 0], :frame-score 0}
                       {:frame 2, :ball-score [10 0], :frame-score 0}
                       {:frame 3, :ball-score [10 0], :frame-score 0}
                       {:frame 4, :ball-score [10 0], :frame-score 0}
                       {:frame 5, :ball-score [10 0], :frame-score 0}
                       {:frame 6, :ball-score [10 0], :frame-score 0}
                       {:frame 7, :ball-score [10 0], :frame-score 0}
                       {:frame 8, :ball-score [10 0], :frame-score 0}
                       {:frame 9, :ball-score [10 0], :frame-score 0}
                       {:frame 10, :ball-score [10 10], :frame-score 0}]))

(def all-9-open (atom [{:frame 0, :ball-score [0 9], :frame-score 0}
                       {:frame 1, :ball-score [0 9], :frame-score 0}
                       {:frame 2, :ball-score [0 9], :frame-score 0}
                       {:frame 3, :ball-score [0 9], :frame-score 0}
                       {:frame 4, :ball-score [0 9], :frame-score 0}
                       {:frame 5, :ball-score [0 9], :frame-score 0}
                       {:frame 6, :ball-score [0 9], :frame-score 0}
                       {:frame 7, :ball-score [0 9], :frame-score 0}
                       {:frame 8, :ball-score [0 9], :frame-score 0}
                       {:frame 9, :ball-score [0 9], :frame-score 0}
                       {:frame 10, :ball-score [0 0], :frame-score 0}]))

(def all-1-9-spare (atom [{:frame 0, :ball-score [1 9], :frame-score 0}
                          {:frame 1, :ball-score [1 9], :frame-score 0}
                          {:frame 2, :ball-score [1 9], :frame-score 0}
                          {:frame 3, :ball-score [1 9], :frame-score 0}
                          {:frame 4, :ball-score [1 9], :frame-score 0}
                          {:frame 5, :ball-score [1 9], :frame-score 0}
                          {:frame 6, :ball-score [1 9], :frame-score 0}
                          {:frame 7, :ball-score [1 9], :frame-score 0}
                          {:frame 8, :ball-score [1 9], :frame-score 0}
                          {:frame 9, :ball-score [1 9], :frame-score 0}
                          {:frame 10, :ball-score [1 0], :frame-score 0}]))

(def all-9-1-spare (atom [{:frame 0, :ball-score [9 1], :frame-score 0}
                          {:frame 1, :ball-score [9 1], :frame-score 0}
                          {:frame 2, :ball-score [9 1], :frame-score 0}
                          {:frame 3, :ball-score [9 1], :frame-score 0}
                          {:frame 4, :ball-score [9 1], :frame-score 0}
                          {:frame 5, :ball-score [9 1], :frame-score 0}
                          {:frame 6, :ball-score [9 1], :frame-score 0}
                          {:frame 7, :ball-score [9 1], :frame-score 0}
                          {:frame 8, :ball-score [9 1], :frame-score 0}
                          {:frame 9, :ball-score [9 1], :frame-score 0}
                          {:frame 10, :ball-score [9 0], :frame-score 0}]))

(def all-spare-and-strike (atom [{:frame 0, :ball-score [1 9], :frame-score 0}
                             {:frame 1, :ball-score [10 0], :frame-score 0}
                             {:frame 2, :ball-score [1 9], :frame-score 0}
                             {:frame 3, :ball-score [10 0], :frame-score 0}
                             {:frame 4, :ball-score [1 9], :frame-score 0}
                             {:frame 5, :ball-score [10 0], :frame-score 0}
                             {:frame 6, :ball-score [1 9], :frame-score 0}
                             {:frame 7, :ball-score [1 9], :frame-score 0}
                             {:frame 8, :ball-score [10 0], :frame-score 0}
                             {:frame 9, :ball-score [1 9], :frame-score 0}
                             {:frame 10, :ball-score [10 0], :frame-score 0}]))

(def incomplete-game (atom [{:frame 0, :ball-score [1 9], :frame-score 0}
                       {:frame 1, :ball-score [10 0], :frame-score 0}
                       {:frame 2, :ball-score [1 9], :frame-score 0}
                       {:frame 3, :ball-score [10 0], :frame-score 0}
                       {:frame 4, :ball-score [1 9], :frame-score 0}
                       {:frame 5, :ball-score [10 0], :frame-score 0}
                       {:frame 6, :ball-score [1 9], :frame-score 0}
                       {:frame 7, :ball-score [1 9], :frame-score 0}
                       {:frame 8, :ball-score [1 9], :frame-score 0}
                       {:frame 9, :ball-score [10 0], :frame-score 0}
                       {:frame 10, :ball-score [], :frame-score 0}]))

(deftest finish?-test
  (testing "All strike game"
    (is (= 300
           (finish? (traverse-board all-strike)))))
  (testing "All open game"
    (is (= 90
           (finish? (traverse-board all-9-open)))))
  (testing "All spare 1-9 game"
    (is (= 110
           (finish? (traverse-board all-1-9-spare)))))
  (testing "All spare 9-1 game"
    (is (= 190
           (finish? (traverse-board all-9-1-spare)))))
(testing "All strike and spare"
    (is (= 191
           (finish? (traverse-board all-spare-and-strike)))))
  (testing "Incomplete game"
    (is (= false
           (finish? (traverse-board incomplete-game))))))
