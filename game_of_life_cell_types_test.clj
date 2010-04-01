(ns game-of-life-cell-types-test
  (:use clojure.test game-of-life-cell-types game-of-life))

(deftest abs?-test
         (is (= 2 (abs 2)))
         (is (= 2 (abs -2)))
         )

(deftest kill-positioned-cell-test
         (is (= [0 [:someposition]] (kill-positioned-cell [1 [:someposition]])))
         (is (= [0 [:someposition]] (kill-positioned-cell [0 [:someposition]])))
         (is (= [0 [:someotherposition]] (kill-positioned-cell [0 [:someotherposition]])))
         )

(deftest live-positioned-cell-test
         (is (= [1 [:someposition]] (live-positioned-cell [1 [:someposition]])))
         (is (= [1 [:someposition]] (live-positioned-cell [0 [:someposition]])))
         (is (= [1 [:someotherposition]] (live-positioned-cell [0 [:someotherposition]])))
         )

(deftest alive-positioned-cell?-test
        (is (= true (alive-positioned-cell? [1 [:someposition]])))
        (is (= false (alive-positioned-cell? [0 [:someposition]])))
         )

(deftest next-to-2D-cartesian?-is-no-next-to-self-test
         (is (= false (next-to-2D-cartesian? [0 [1 1]] [0 [1 1]]))))


(deftest next-to-2D-cartesian?-orthogonal-test
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [3 2]])))
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [3 4]])))
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [4 3]])))
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [3 2]]))))

(deftest next-to-2D-cartesian?-diags-test
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [2 2]])))
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [2 4]])))
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [4 2]])))
         (is (= true (next-to-2D-cartesian? [0 [3 3]] [0 [4 4]]))))

(deftest next-to-2D-cartesian?-diags-test
         (is (= false (next-to-2D-cartesian? [0 [3 3]] [0 [5 2]])))
         (is (= false (next-to-2D-cartesian? [0 [3 3]] [0 [5 5]])))
         (is (= false (next-to-2D-cartesian? [0 [3 3]] [0 [3 1]])))
         (is (= false (next-to-2D-cartesian? [0 [3 3]] [0 [-2 -2]]))))

(deftest single-tick-stable-block-test
         (let [state-changer (state-changer-factory alive-positioned-cell? kill-positioned-cell live-positioned-cell)
               initial  [[1 [0 0]] [1 [0 1]] 
                         [1 [1 0]] [1 [1 1]]] 
               expected [[1 [0 0]] [1 [0 1]] 
                         [1 [1 0]] [1 [1 1]]]]
           (is (= expected (single-tick initial (create-neighborhoods initial next-to-2D-cartesian?) state-changer))))
         )

(deftest single-tick-stable-blinker-test
         (let [state-changer (state-changer-factory alive-positioned-cell? kill-positioned-cell live-positioned-cell)
               up-down  [
                         [0 [0 0]] [1 [0 1]] [0 [0 2]] 
                         [0 [1 0]] [1 [1 1]] [0 [1 2]] 
                         [0 [2 0]] [1 [2 1]] [0 [2 2]] ]
               side-to-side [
                         [0 [0 0]] [0 [0 1]] [0 [0 2]] 
                         [1 [1 0]] [1 [1 1]] [1 [1 2]] 
                         [0 [2 0]] [0 [2 1]] [0 [2 2]] ]
               ]
           (is (= side-to-side (single-tick up-down (create-neighborhoods up-down next-to-2D-cartesian?) state-changer)))
           (is (= up-down (single-tick side-to-side (create-neighborhoods side-to-side next-to-2D-cartesian?) state-changer))))
         )

(run-tests 'game-of-life-cell-types-test)
