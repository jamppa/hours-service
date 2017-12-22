(ns hours-service.system
  (:require
    [com.stuartsierra.component :as component]
    [hours-service.components.env :refer [new-env new-test-env]]
    [hours-service.components.db :refer [new-db]]
    [hours-service.components.broker :refer [new-broker]]
    [hours-service.components.app :refer [new-app]]
    [hours-service.components.handler :refer [new-handler]]
    [hours-service.customers.customer-handler :as customer-handler]))

(def system nil)

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

(defn init []
  (alter-var-root #'system (constantly (system-map))))

(defn init-test []
  (alter-var-root #'system (constantly (test-system-map))))

(defn start []
  (alter-var-root #'system component/start))


(defn stop []
  (alter-var-root #'system component/stop))
