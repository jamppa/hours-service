(ns hours-service.components.handler
  (:require
    [com.stuartsierra.component :as component]
    [hours-service.components.broker :refer [poll]]
    [cheshire.core :as cheshire]))

(defmulti handle-command (fn [command app] (:type command)))

(defmethod handle-command :default
  [command app]
    (println "no command handler found!")
    (println command))

(defn- handle-record [app record]
  (->
    (.value record)
    (cheshire/parse-string true)
    (handle-command app)))

(defrecord Handler [app]
  component/Lifecycle

  (start [component]
    (println ";; Starting Handler")
    (poll (:broker app) #(handle-record app %)))

  (stop [component]
    (println ";; Stopping Handler"))
)

(defn new-handler []
  (Handler. nil))
