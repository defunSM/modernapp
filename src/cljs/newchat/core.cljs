(ns newchat.core
  (:require   [clojure.core]
              [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; ------------------------
;; Database

;; (def database-connection (env :database-url "postgres://jlhkvxviinvkpy:7hkCi_Cc_4id02UMci4-TiOVeR@ec2-54-225-64-254.compute-1.amazonaws.com:5432/d7lei527gbu5v"))


;; (defn record [db input]
;;   (db/insert! database-connection db {:content input}))

;; -------------------------
;; Views

(defonce stuff (reagent/atom ["one" "two"]))

(defn home-page []
  [:div [:center [:h2 "SMchat"]]
   [:center  [:h3 "The Modern Chat Platform"]]
;;   [:div [:a {:href "/about"} "go to about page"]]
   [:br] [:br]
   [:center [:div1 [:a {:href "https://defunsm.github.io/posts-output/smchat/"} "SMChat"]]
    [:br] [:br] [:br]
    [:div1 [:a {:href "/about"} "Messages"]]]
   [:br] [:br] [:br]
   [:center [:img {:src "http://imgur.com/StGANnEl.png"}]]])

(defn about-page []
  [:div [:center [:h2 " SMchat"]]
   [:br]
   [:center [:div1 [:a {:href "/"} "Home"]]]
   [:br] [:br]
   [:textbox [:center "ChatBox :"]
    [:br]
    [:br]
    [:center [:div1"Currently under active development"]]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
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
