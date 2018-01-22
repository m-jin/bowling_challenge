(ns bowling-challenge.core-test
  (:require [clojure.test :refer :all]
            [bowling-challenge.core :refer :all]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

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

(def all-spare-strike-alternate (atom [{:frame 0, :ball-score [1 9], :frame-score 0}
                                       {:frame 1, :ball-score [10 0], :frame-score 0}
                                       {:frame 2, :ball-score [1 9], :frame-score 0}
                                       {:frame 3, :ball-score [10 0], :frame-score 0}
                                       {:frame 4, :ball-score [1 9], :frame-score 0}
                                       {:frame 5, :ball-score [10 0], :frame-score 0}
                                       {:frame 6, :ball-score [1 9], :frame-score 0}
                                       {:frame 7, :ball-score [1 9], :frame-score 0}
                                       {:frame 8, :ball-score [10 0], :frame-score 0}
                                       {:frame 9, :ball-score [1 9], :frame-score 0}
                                       {:frame 10, :ball-score [10], :frame-score 0}]))

(def incomplete (atom [{:frame 0, :ball-score [1 9], :frame-score 0}
                       {:frame 1, :ball-score [10 0], :frame-score 0}
                       {:frame 2, :ball-score [1 9], :frame-score 0}
                       {:frame 3, :ball-score [10 0], :frame-score 0}
                       {:frame 4, :ball-score [1 9], :frame-score 0}
                       {:frame 5, :ball-score [10 0], :frame-score 0}
                       {:frame 6, :ball-score [1 9], :frame-score 0}
                       {:frame 7, :ball-score [1 9], :frame-score 0}
                       {:frame 8, :ball-score [], :frame-score 0}
                       {:frame 9, :ball-score [], :frame-score 0}
                       {:frame 10, :ball-score [], :frame-score 0}]))

(deftest finish!
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
  (testing "Alternating spare strike"
    (is (= false
           (finish? (traverse-board all-spare-strike-alternate))))))
