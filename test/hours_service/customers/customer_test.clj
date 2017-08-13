(ns hours-service.customers.customer-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.customers.customer :as customer]
    [hours-service.fixtures :as fixtures]))

(def valid-customer-data {
  :name "The Firm Ltd"
  :business-id "1234567-8"})


(fact "valid customer contains the id"
  (customer/new valid-customer-data) => (contains {:_id anything}))


(fact "valid customer contains the data"
  (customer/new valid-customer-data) => (contains valid-customer-data))


(fact "customer created -event contains type and the customer"
  (customer/new-created fixtures/valid-customer) =>
    (contains {:type "customer-created" :data fixtures/valid-customer}))


(fact "customer creation failed -event contains the type and the customer"
  (customer/new-creation-failed fixtures/valid-customer) =>
    (contains {:type "customer-creation-failed" :data fixtures/valid-customer}))
