(ns newchat.core
  (:require   [reagent.core :as reagent :refer [atom]]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]
              [clojure.string :as str]
              [homepage.core :as hp]
              [about.core :as ab]
              [planets.core :as p]
              [mathpage.core :as m]))

;; ------------------------
;; Database

;; (def database-connection (env :database-url "postgres://jlhkvxviinvkpy:7hkCi_Cc_4id02UMci4-TiOVeR@ec2-54-225-64-254.compute-1.amazonaws.com:5432/d7lei527gbu5v"))


;; (defn record [db input]
;;  (db/insert! database-connection db {:content input}))

;; -------------------------
;; Views


(defonce timer (reagent/atom (js/Date.)))
(defonce time-updater (js/setInterval #(reset! timer (js/Date.)) 1000))

(defn home-page []
  (let [timer-str (-> @timer .toTimeString (str/split " ") first)]
      (hp/home timer-str)))

(defn about-page []
  (ab/aboutpage))

(defn paperless-math []
  (let [timer-str (-> @timer .toTimeString (str/split " ") first)]
    (m/paperlessmath timer-str)))

(defn planetpage []
  (let [timer-str (-> @timer .toTimeString (str/split " ") first)]
    (p/planets timer-str)))

(defn webviewer []
  [:div "Testing"])

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

(secretary/defroute "/webviewer" []
  (session/put! :current-page #'webviewer))

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
