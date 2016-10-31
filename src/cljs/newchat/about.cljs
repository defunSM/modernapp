(ns about.core
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce text (reagent/atom ""))
(defonce arrow (reagent/atom "Press for More Info"))
(defonce nav-height (reagent/atom "50px"))
(defonce arrowmessage (reagent/atom ""))

(defn save []
  (do (reset! text "")))

(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))
           :on-key-down #(case (.-which %)
                                      13 (save)
                                      nil)}])
(defn arrow-fn []
  (if (= @arrow "Press for More Info")
    (do (reset! nav-height "200px")
        (reset! arrow "x")
        (reset! arrowmessage "Currently under development. Working on implementing natural language processes. Working on front-end development as of now. Some of the technologies being used here are Clojure, JavaScript, Reagent, and React."))
    (do (reset! nav-height "50px")
        (reset! arrow "Press for More Info")
        (reset! arrowmessage ""))))

(defn aboutpage []
  [:div
   [:center [:h2 "SMchat"]]
   [:center
    [:p "Preview:    "]
    [:p @text]
    [:p "Send your message : "]
    [atom-input text]
    [:br] [:br]
    [:a {:href "/"} "Home"]]
   [:nav.arrow {:style {:height @nav-height}} [:button {:on-click #(arrow-fn)} @arrow]
    [:p {:style {:font-size "12px" :margin "20px"}} @arrowmessage ]]])
