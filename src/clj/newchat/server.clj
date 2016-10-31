(ns newchat.server
  (:require [newchat.handler :refer [app]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]
            ;;            [clojure.java.jdbc :as db]
            [clojure.java.jdbc :as db])
  (:gen-class))

(def database-connection (env :database-url "postgres://jlhkvxviinvkpy:7hkCi_Cc_4id02UMci4-TiOVeR@ec2-54-225-64-254.compute-1.amazonaws.com:5432/d7lei527gbu5v"))

(defn record [db input]
 (db/insert! database-connection db {:content input}))

(defn -main [& args]
  (let [port (Integer/parseInt (or (env :port) "3000"))]
    (run-jetty app {:port port :join? false})))
