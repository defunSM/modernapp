(ns planets.core
  (:require [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]))

(defonce universe (reagent/atom {:time 0
                                 :numberofplanets 0
                                 :text ""
                                 :display ""}))

(defonce time-updater (js/setInterval #(swap! universe update-in [:time] + 1) 1000))

(defn reset-time []
  (swap! universe assoc-in [:time] 0)
  (println @universe))

(defn creategap [num]
  (for [i (range 0 num)] [:br]))

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

(defn setup-planet [left top size background-color]
  (swap! universe assoc-in
         [:planets (keyword (str (:numberofplanets @universe)))] {:position "absolute"
                                                                  :left (str left "px")
                                                                  :top (str top "px")
                                                                  :height size
                                                                  :width size
                                                                  :background-color background-color})
  (swap! universe update-in [:numberofplanets] + 1)
  (println @universe))


(defn create-planet [left top size background-color]
  [:div.planet {:style {:position "absolute"
                        :left left
                        :top top
                        :height size
                        :width size
                        :background-color background-color}}])

(defn render-planets []
  (let [num (count (:planets @universe))
        planet-data (:planets @universe)]
    (for [i planet-data]
      (let [planet-info (first (rest i))]
        (create-planet (:left planet-info)
                       (:top planet-info)
                       (:height planet-info)
                       (:background-color planet-info))))))

(defn clear-display []
  (swap! universe assoc-in [:display] ""))

(defn blinking-text [message]
  (let [num (count (clojure.string/split message #""))
        array (clojure.string/split message #"")]
    (for [i (range 1 num)]
      (swap! universe assoc-in [:display] (str (:display @universe) (nth array i))))))

(defn return-pressed []
  (swap! universe assoc-in [:display] (:text @universe))
  (swap! universe assoc-in [:text] ""))

(defn atom-input [value]
  [:input {:type "text"
           :value (:text @value)
           :on-change #(swap! value assoc-in [:text] (-> % .-target .-value))
           :on-key-down #(case (.-which %)
                                      13 (return-pressed)
                                      nil)
           :style {:position "relative" :top "-100px"}}])

(defn planets [timer-str]
  [:div (page-layout timer-str)
   [:center (:display @universe)]
   [:center (atom-input universe)]
   [:center (render-planets)]])

;; (for [i (range 1 2)] (create-planet (movement i 10 3)
;;                                                 (y-pos i 20)
;;                                                 (planet-size)
;;                                                 (random-color)))

;; (defn random-color []
;;   (str "#" (clojure.string/join (take 6 (repeatedly #(rand-int 10))))))

;; (defn movement [i gap speed]
;;   (+ (* i gap) (* speed (:time @universe))))

;; (defn y-pos [i num]
;;   num)

;; (defn planet-size []
;;   30)
