(ns hours-service.components.db
  (:require
    [com.stuartsierra.component :as component]
    [monger.core :as mg]))

(defrecord DB [env]
  component/Lifecycle

  (start [component]
    (println ";; Starting DB")
    (let [conn (mg/connect (get-in env [:config :db]))
          db (mg/get-db conn "hours")]
        (merge component {:db db :connection conn})))

  (stop [component]
    (println "Stopping DB")
    (let [conn (:connection component)]
      (mg/disconnect conn)
      (merge component {:db nil :connection nil})))

)

(defn new-db []
  (DB. nil))
