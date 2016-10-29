(ns homepage.core)

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
                    :font-size "12px"}} timer-str]])
