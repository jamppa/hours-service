(ns hours-service.customers.handler-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.components.handler :as handler]
    [hours-service.fixtures :as fixtures]
    [hours-service.system :as s]
    [hours-service.customers.repo :as repo]))

(background
  (before :facts (do (s/init-test) (s/start) (fixtures/load-fixtures (:db s/system))))
  (after :facts (s/stop)))

(def create-customer-cmd {
  :type "create-customer"
  :data {:name "Test Oy" :business-id "123456-7"}
})

(fact "create-customer command creates new customer to repository"
  (handler/handle-command create-customer-cmd (:app s/system))
  (repo/find-by-name (:db s/system) "Test Oy") => (contains (:data create-customer-cmd)))