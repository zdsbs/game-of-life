(ns game-of-life-test
  (:use clojure.test game-of-life))

(defn alive-cell-is-0-or-1? [cell]
  (if (= cell 0) false
    true))

(defn kill-as-0 [cell] 0)

(defn live-as-1 [cell] 1)

(def cell-state-changer-0-1
  (state-changer-factory alive-cell-is-0-or-1? kill-as-0 live-as-1))

(deftest cell-state-changer-test
         (is (= 0 (cell-state-changer-0-1 1 [0 0 1])))
         (is (= 0 (cell-state-changer-0-1 1 [1 1 1 1])))
         (is (= 1 (cell-state-changer-0-1 1 [1 1])))
         (is (= 1 (cell-state-changer-0-1 1 [1 1 1])))
         (is (= 0 (cell-state-changer-0-1 0 [0 0 1])))
         (is (= 0 (cell-state-changer-0-1 0 [0 1 1])))
         (is (= 1 (cell-state-changer-0-1 0 [1 1 1])))
         (is (= 0 (cell-state-changer-0-1 0 [1 1 1 1])))
         )

(deftest single-tick-test
         (is (= [0]   (single-tick [1 1]        [[1]]             cell-state-changer-0-1)))
         (is (= [0]   (single-tick [1 1 0]      [[1 0]]           cell-state-changer-0-1)))
         (is (= [0]   (single-tick [1 0]        [[0]]             cell-state-changer-0-1)))
         (is (= [1]   (single-tick [1 1 1]      [[1 1]]           cell-state-changer-0-1)))
         (is (= [0]   (single-tick [0 1 1]      [[1 1]]           cell-state-changer-0-1)))
         (is (= [0]   (single-tick [0 1 1 1 1]  [[1 1 1 1]]       cell-state-changer-0-1)))
         (is (= [1]   (single-tick [0 1 1 1]    [[1 1 1]]         cell-state-changer-0-1)))
         (is (= [0 0] (single-tick [0 0 1 1]    [[0 1 1] [0 1 1]] cell-state-changer-0-1)))
         (is (= [0 0] (single-tick [1 1]        [[1] [1]]         cell-state-changer-0-1)))
         (is (= [1 1] (single-tick [1 1 1]      [[1 1] [1 1]]     cell-state-changer-0-1)))
         (is (= [1 1] (single-tick [1 0 1 1]    [[0 1 1] [1 1 1]] cell-state-changer-0-1)))
         (is (= [1 1] (single-tick [1 1 1 1]    [[1 1 1] [1 1 1]] cell-state-changer-0-1)))
         )

(defn always-next-to-true? [cellA cellB] true)

(deftest create-neighborhoods-test
          (is (= [[0 1] [0 1]] (create-neighborhoods [0 1] always-next-to-true?)))
         )
(run-tests 'game-of-life-test)
