(ns untitled1.core
  (:require [promesa.core :as p]
            [clojure.core.async
             :as a
             :refer [onto-chan >! <! >!! <!! go chan close! go-loop]]))

; >>>>>>>>> Лаба 2 <<<<<<<<<<

(defn vec-to-chan [vec]
    (let [c (chan)
          v (conj vec "end")]
      (go (doseq [x v]
                (>! c x))
            (close! c))
  c))

(defn main [c]
    (a/<!!
      (a/go-loop [Result [] Temp [] Count 0 i 0]
        (if-let [x (<! c)]
          (if (= i 0)
            (recur Result [] x (inc i))
            (do
              (if (= Count 0)
                (recur (conj Result Temp) [] x i)
                (recur Result (conj Temp x) (dec Count) i)
                ))
            )
          Result)
      )))


(def ch (vec-to-chan[3 4 0 2 1 2 2 4 5]))
(println (main ch))


