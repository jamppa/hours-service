(ns hours-service.system
  (:require
    [com.stuartsierra.component :as component]
    [hours-service.components.env :refer [new-env new-test-env]]
    [hours-service.components.db :refer [new-db]]
    [hours-service.components.broker :refer [new-broker]]
    [hours-service.components.app :refer [new-app]]
    [hours-service.components.handler :refer [new-handler]]
    [hours-service.customers.handler]))

(defn system-map []
  (component/system-map
    :env (new-env)
    :db (component/using (new-db) [:env])
    :broker (component/using (new-broker) [:env])
    :app (component/using (new-app) [:broker :db])
    :handler (component/using (new-handler) [:app])))

(defn test-system-map []
  (component/system-map
    :env (new-test-env)
    :db (component/using (new-db) [:env])
    :broker (component/using (new-broker) [:env])
    :app (component/using (new-app) [:broker :db])))

(defn start [system]
  (component/start system))

(defn stop [system]
  (component/stop system))
