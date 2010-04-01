(ns game-of-life-cell-types)
;positioned-cell

(defn abs [x] 
  (if (neg? x) (* -1 x) x))

(defn kill-positioned-cell [[_ position]]
  [0 position])

(defn live-positioned-cell [[_ position]]
  [1 position])

(defn alive-positioned-cell? [[nature _]]
  (= nature 1))

(defn next-to-2D-cartesian? [[_ [x1 y1]] [_ [x2 y2]]]
  (if (and (= x1 x2) (= y1 y2)) 
    false
    (let [delta-x (abs (- x1 x2))
          delta-y (abs (- y1 y2))]
        (and (< delta-x 2) (< delta-y 2))
      )))

