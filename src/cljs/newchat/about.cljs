(ns about.core
  (:require [reagent.core :as reagent :refer [atom]]))

(defonce text (reagent/atom ""))
(defonce arrow (reagent/atom "^"))
(defonce nav-height (reagent/atom "50px"))

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
  (if (= @arrow "^")
    (do (reset! nav-height "100px")
        (reset! arrow "x"))
    (do (reset! nav-height "50px")
        (reset! arrow "^"))))

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
   [:nav.arrow {:style {:height @nav-height}} [:arrow {:on-click #(arrow-fn)} @arrow]]])
