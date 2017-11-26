(ns hours-service.customers.handler
  (:require
    [hours-service.components.handler :as handler]
    [hours-service.customers.customer :as customer]
    [hours-service.customers.repo :as repo]))

(defmethod handler/handle-command "create-customer"
  [command app]
  (->>
    (customer/new (:data command))
    (repo/save (:db app))))
