(ns drawingpad.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def w 400)
(def h 400)

(defn instructions []
  (q/fill 255 100 100)
  (q/text "Instructions to help you draw cool things!" 100 100)
  (q/text "1. To save an image all you have to do is press 's' and allow the pop up." 100 140)
  (q/text "2. To clear the current canvas press 'c' careful not to press it by mistake." 100 180)
  (q/text "3. To make random circles on the canvas press 'r'." 100 220)
  (q/text "4. Use arrow keys to create circles that follow a path from the left top corner." 100 260)
  (q/text "5. Have fun!" 100 300))



(defn setup []
  (q/frame-rate 20)
  (q/background 255)
  (q/text-size 20)
  (instructions)
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
  (if (= (q/key-code) "i")
    (instructions))
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
