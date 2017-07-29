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
      (assoc component :db db :conn conn)))

  (stop [component]
    (println ";; Stopping DB")
    (let [conn (:conn component)]
      (mg/disconnect conn)))
)

(defn new-db []
  (DB. nil))
