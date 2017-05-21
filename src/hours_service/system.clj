(ns hours-service.system
  (:require
    [com.stuartsierra.component :as component]
    [hours-service.components.env :refer [new-env]]
    [hours-service.components.db :refer [new-db]]
    [hours-service.components.broker :refer [new-broker]]
    [hours-service.components.app :refer [new-app]]
    [hours-service.components.handler :refer [new-handler]]
    [hours-service.customers.handler]))

(defn system []
  (component/system-map
    :env (new-env)
    :db (component/using (new-db) [:env])
    :broker (component/using (new-broker) [:env])
    :app (component/using (new-app) [:broker :db])
    :handler (component/using (new-handler) [:app])))

(defn start []
  (component/start (system)))
