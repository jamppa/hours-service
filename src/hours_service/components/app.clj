(ns hours-service.components.app
  (:require
    [com.stuartsierra.component :as component]))

(defrecord App [db broker]
  component/Lifecycle

  (start [component]
    (println ";; Starting App")
    (merge component {:db db :broker broker}))

  (stop [component]
    (println ";; Stopping App")
    (merge component {:db nil :broker nil}))
)

(defn new-app []
  (App. nil nil))
