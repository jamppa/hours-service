(ns hours-service.customers.model-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.customers.model :as customers-model]))

(def valid-customer-data {
  :name "The Firm Ltd"
  :business-id "1234567-8"})

(fact "new valid customer has the id"
  (customers-model/new valid-customer-data) => (contains {:_id anything}))

(fact "new valid customer has the data in it"
  (customers-model/new valid-customer-data) => (contains valid-customer-data))
