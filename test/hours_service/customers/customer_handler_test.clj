(ns hours-service.customers.customer-handler-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.components.handler :as handler]
    [hours-service.components.broker :as broker]
    [hours-service.fixtures :as fixtures]
    [hours-service.system :as s]
    [hours-service.customers.customer-repo :as repo]))

(background
  (before :facts (do (s/init-test) (s/start) (fixtures/load-fixtures (:db s/system))))
  (after :facts (s/stop)))

(def create-customer-cmd {
  :type "create-customer"
  :data {:name "Test Oy" :business-id "123456-7"}
})

(fact "creates new customer to repository"
  (handler/handle-command create-customer-cmd (:app s/system))
  (repo/find-by-name (:db s/system) "Test Oy") => (contains (:data create-customer-cmd)))

(fact "sends event to broker on successful creation"
  (handler/handle-command create-customer-cmd (:app s/system)) => anything
  (provided
    (broker/send-successful-event anything anything) => anything :times 1))

(fact "sends failed event to broker when saving to repo fails"
  (handler/handle-command create-customer-cmd (:app s/system)) => anything
    (provided
      (repo/save anything anything) => (throws Exception "save failed!")
      (broker/send-failed-event anything anything) => anything :times 1))