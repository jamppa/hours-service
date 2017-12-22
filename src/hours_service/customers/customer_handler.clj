(ns hours-service.customers.customer-handler
  (:require
    [hours-service.components.handler :as handler]
    [hours-service.components.broker :as broker]
    [hours-service.customers.customer :as customer]
    [hours-service.customers.customer-repo :as repo]))

(defn- customer-created [broker customer]
  (->> 
    (customer/new-created-event customer)
    (broker/send-successful-event broker)))

(defmethod handler/handle-command "create-customer"
  [command app]
  (->>
    (customer/new (:data command))
    (repo/save (:db app))
    (customer-created (:broker app))))
