(ns planets.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]))

(defonce universe (reagent/atom {:time 0}))

(defonce timeofuniverse (reagent/atom 0))

(defonce time-updater (js/setInterval #(swap! universe update-in [:time] + 1) 1000))

(defn reset-time []
  (swap! universe assoc-in [:time] 0)
  (println @universe))

(defn creategap [num]
  (for [i (range 0 num)] [:br]))

(defn create-planet [left top size background-color]
  [:div.planet {:style {:position "absolute"
                        :left (str left "px")
                        :top (str top "px")
                        :height size
                        :width size
                        :background-color background-color}}])

(defn page-layout [timer-str]
  [:div      [:center [:h2 "Planet Simulation"]]
   [:p {:style {:padding "30px"
                :align "bottom"
                :position "absolute"
                :top "850px"
                :font-size "12px"}} timer-str]
   [:p {:style {:padding "0px 30px"
                :align "bottom"
                :position "absolute"
                :top "850px"}} "Copyright © Salman H. 2016"]
   [:center [:a {:href "/"} "Home"]]
   (creategap 5)])

(defn random-color []
  (str "#" (clojure.string/join (take 6 (repeatedly #(rand-int 10))))))

(defn movement [i gap speed]
  (+ (* i gap) (* speed (:time @universe))))

(defn y-pos [i num]
  num)

(defn planet-size []
  10)



(defn planets [timer-str]
  [:div (page-layout timer-str)
   [:center (for [i (range 1 2)] (create-planet (movement i 10 3) (y-pos i 20) (planet-size) (random-color)))]])
