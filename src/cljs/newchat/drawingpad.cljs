(ns drawingpad.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def w 400)
(def h 400)

(defn setup []
  (q/frame-rate 20)
  (q/background 255)
  (q/text-size 20)
  (q/text "Touch to start drawing!" 50 50 50))

(defonce mx (atom 0))
(defonce my (atom 0))

(defn move-up []
  (println (q/key-code) "- UP")
  (swap! my - 5)
  (println @my)
  (let [diam (q/random 20)]
    (q/ellipse @mx @my diam diam)))

(defn move-down []
  (println (q/key-code) "- DOWN")
  (swap! my + 5)
  (println @my)
  (let [diam (q/random 20)]
    (q/ellipse @mx @my diam diam)))

(defn move-left []
  (println (q/key-code) "- LEFT")
  (swap! mx - 5)
  (println @mx)
  (let [diam (q/random 20)]
    (q/ellipse @mx @my diam diam)))

(defn move-right []
  (println (q/key-code) "- RIGHT")
  (swap! mx + 5)
  (println @mx)
  (let [diam (q/random 20)]
    (q/ellipse @mx @my diam diam)))

(defn draw []
  (q/stroke (q/random 255) (q/random 255) (q/random 255))
  (q/stroke-weight (q/random 10))
  (q/fill (q/random 255) (q/random 255) (q/random 255)))

(defn mouse-clicked []
  (let [diam (q/random 50)]
    (q/ellipse (q/mouse-x) (q/mouse-y) diam diam)))

(defn keypress []
  (if (= (q/key-code) 39)
    (do (move-right)))
  (if (= (q/key-code) 40)
    (do (move-down)))
  (if (= (q/key-code) 38)
    (do (move-up)))
  (if (= (q/key-code) 37)
    (do (move-left)))
  (if (= (.toString (q/raw-key)) "s")
    (do (q/save-frame "pretty-pic.jpg")
        (println "Picture saved to pretty-pic.jpg")))
  (if (= (.toString (q/raw-key)) "r")
    (do (let [diam (q/random 20)
              x (q/random (q/width))
              y (q/random (q/height))]
          (q/ellipse x y diam diam))))
  (if (= (.toString (q/raw-key)) "a")
    (do (let [diam (q/random 20)
              x (q/random (q/height))
              y (q/random (q/height))]
          (q/ellipse x y diam diam))))
  (if (= (.toString (q/raw-key)) "c")
    (do (let [diam 50
              x 50
              y 50]
          (q/fill 255 255 255)
          (q/stroke 255)
          (q/rect 0 0 10000 10000)))))

(q/defsketch cljs-quil
  :host "cljs-quil"
  :title "Circles"
  :settings #(q/smooth 2)
  :setup setup
  :draw draw
  :size [1000 1000]
  :key-pressed keypress
  :mouse-pressed mouse-clicked
  :mouse-dragged mouse-clicked)

(defn drawingpad-page []
    [:canvas#cljs-quil])
