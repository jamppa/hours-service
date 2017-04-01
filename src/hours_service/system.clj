(ns hours-service.system
  (:require
    [com.stuartsierra.component :as component]
    [hours-service.components.env :refer [new-env]]
    [hours-service.components.db :refer [new-db]]))

(defn system []
  (component/system-map
    :env (new-env)
    :db (component/using (new-db) [:env])))

(defn start []
  (component/start (system)))
