(ns homepage.core
  (:require [reagent.core :as reagent :refer [atom]]))


(defonce text (reagent/atom ""))
(defonce arrow (reagent/atom "Press here for more information"))
(defonce nav-height (reagent/atom "50px"))
(defonce arrowmessage (reagent/atom ""))


(defn arrow-fn []
  (if (= @arrow "Press here for more information")
    (do (reset! nav-height "200px")
        (reset! arrow "x")
        (reset! arrowmessage "This is a currently a heroku website that is being used to hold various projects that I am working on. There is the SMChat which is a chat client, the Paperless Math Project which is aimed for doing calculations. Finally the Planet Simulation which will simulate orbitals of Planets."))
    (do (reset! nav-height "50px")
        (reset! arrow "Press here for more information")
        (reset! arrowmessage ""))))

(defn home [timer-str]
  [:div [:center [:h2 "SMchat"]]
       [:center  [:h3 "The Modern Chat Platform"]]
       ;;   [:div [:a {:href "/about"} "go to about page"]]
       [:br] [:br]
       [:center [:div1 [:a {:href "https://defunsm.github.io/posts-output/smchat/"} "SMChat"]]
        [:br] [:br] [:br]
        [:div1 [:a {:href "/about"} "Messages"]]]
       [:br] [:br] [:br]
       [:center [:img {:src "http://imgur.com/StGANnEl.png"}]]
       [:br] [:br] [:br]
       [:center [:h2 "Paperless Math"]]
       [:center [:h3 "A Multi Purpose Math Utility"]]
       [:br] [:br]
       [:center [:div1 [:a {:href "/math"} "Paperless Math"]]]
   [:br] [:br] [:br]
   [:center [:h2 "Planet Simulation"]]
   [:center [:h3 "A Colorful Simulation of Planetary Orbits"]]
   [:br] [:br]
   [:center [:div1 [:a {:href "/planet"} "Simulation"]]]
   [:br] [:br]
       [:p {:style {:padding "0px 30px"}} "Copyright © Salman H. 2016"]
       [:p {:style {:padding "0px 30px"
                    :align "bottom"
                    :fixed 0
                    :bottom 0
                    :font-size "12px"}} timer-str]
      [:nav.arrow {:style {:height @nav-height}} [:arrow {:on-click #(arrow-fn)} @arrow]
       [:p {:style {:font-size "12px" :margin "20px"}} @arrowmessage ]]]
  )
