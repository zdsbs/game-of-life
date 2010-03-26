(ns game-of-life)

(defn state-changer-factory [alive? kill live]
  (fn [cell neighbors] 
   (let [num-live-neighbors (count (filter alive? neighbors))]
      (cond (and (not (alive? cell)) (= num-live-neighbors 2)) (kill cell)
          (< num-live-neighbors 2) (kill cell)
          (> num-live-neighbors 3) (kill cell)
          :else (live cell)))))

(defn single-tick [cells neighborhoods state-changer]
  (map state-changer cells neighborhoods))

