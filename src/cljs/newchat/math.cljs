(ns mathpage.core
  (:require [reagent.core :as reagent :refer [atom]]
            [clojure.string :as str]))


(defonce text (reagent/atom ""))
(defonce stuff (reagent/atom ["one" "two"]))

(defonce numbers (reagent/atom ""))
(defonce display (reagent/atom ""))


(defn save []
  (do (reset! text "")))

(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))
           :on-key-down #(case (.-which %)
                                      13 (save)
                                      nil)}])

(defn rearrange [array]
  (let [num (count array)]
    (for [i (range 0 num)]
     (nth array i))))

(defn filter-math [text]
  (let [new (rearrange (str/split text #""))]
    new))

(defn paperlessmath [timer-str]
  [:div
     [:center [:h2 "Paperless Math"]]
     [:center
      [:p @display]
      [:p (filter-math @text)]
      [atom-input text]]
     [:p {:style {:padding "30px"
                  :align "bottom"
                  :position "absolute"
                  :top "850px"
                  :font-size "12px"}} timer-str]
     [:p {:style {:padding "0px 30px"
                  :align "bottom"
                  :position "absolute"
                  :top "850px"}} "Copyright © Salman H. 2016"]
     [:center [:a {:href "/"} "Home"]]])
