(ns hours-service.customers.handler
  (:require
    [hours-service.components.handler :as handler]))

(defmethod handler/handle-command "create-customer"
  [command app]
    (println command))
