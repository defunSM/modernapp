(ns newchat.core
  (:require   [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [clojure.string :as str]
              [homepage.core :as hp]
              [about.core :as ab]
              [planets.core :as p]))

;; ------------------------
;; Database

;; (def database-connection (env :database-url "postgres://jlhkvxviinvkpy:7hkCi_Cc_4id02UMci4-TiOVeR@ec2-54-225-64-254.compute-1.amazonaws.com:5432/d7lei527gbu5v"))


;; (defn record [db input]
;;   (db/insert! database-connection db {:content input}))

;; -------------------------
;; Views

(defonce timer (reagent/atom (js/Date.)))
(defonce time-updater (js/setInterval #(reset! timer (js/Date.)) 1000))

(defonce text (reagent/atom ""))
(defonce stuff (reagent/atom ["one" "two"]))

(defonce numbers (reagent/atom ""))
(defonce display (reagent/atom ""))


(defn home-page []
  (let [timer-str (-> @timer .toTimeString (str/split " ") first)]
      (hp/home timer-str)))

(defn save []
  (do (reset! text "")))

(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))
           :on-key-down #(case (.-which %)
                                      13 (save)
                                      nil)}])

(defn about-page []
      [:div
       [:center [:h2 "SMchat"]]
       [:center
        [:p "Preview:    "]
        [:p @text]
        [:p "Send your message : "]
        [atom-input text]
        [:br] [:br]
        [:a {:href "/"} "Home"]]])

(defn rearrange [array]
  (let [num (count array)]
    (for [i (range 0 num)]
     (nth array i))))

(defn filter-math [text]
  (let [new (rearrange (str/split text #""))]
    new))

(defn paperless-math []
  (let [timer-str (-> @timer .toTimeString (str/split " ") first)]
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
     [:center [:a {:href "/"} "Home"]]]))

(defn planetpage []
  (let [timer-str (-> @timer .toTimeString (str/split " ") first)]
    (p/planets timer-str)))

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

(secretary/defroute "/math" []
  (session/put! :current-page #'paperless-math))

(secretary/defroute "/planet" []
  (session/put! :current-page #'planetpage))
;; -------------------------
;; Initialize app

(defn ^:export mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
