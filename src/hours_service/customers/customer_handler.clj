(ns hours-service.customers.customer-handler
  (:require
    [hours-service.components.handler :as handler]
    [hours-service.components.broker :as broker]
    [hours-service.customers.customer :as customer]
    [hours-service.customers.customer-repo :as repo]
    [slingshot.slingshot :as s]))

(defn- customer-created [broker customer]
  (->> 
    (customer/new-created-event customer)
    (broker/send-successful-event broker)))

(defn- customer-creation-failed [broker reason]
  (->>
    (customer/new-creation-failed-event reason)
    (broker/send-failed-event broker)))

(defmethod handler/handle-command "create-customer"
  [command app]
  (s/try+
    (->>
      (customer/new (:data command))
      (repo/save (:db app))
      (customer-created (:broker app)))
  (catch Exception e
    (customer-creation-failed (:broker app) (:message e)))))
