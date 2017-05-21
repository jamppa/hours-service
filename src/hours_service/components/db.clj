(ns hours-service.components.db
  (:require
    [com.stuartsierra.component :as component]
    [monger.core :as mg]))

(defrecord DB [env]
  component/Lifecycle

  (start [component]
    (println ";; Starting DB")
    (let [uri (get-in env [:config :db :url])
          {:keys [conn db]} (mg/connect-via-uri uri)]
      (merge component {:db db :connection conn})))

  (stop [component]
    (println ";; Stopping DB")
    (let [conn (:connection component)]
      (mg/disconnect conn)
      (merge component {:db nil :connection nil})))
)

(defn new-db []
  (DB. nil))
