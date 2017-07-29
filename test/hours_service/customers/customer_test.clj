(ns hours-service.customers.customer-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.customers.customer :as customer]))

(def valid-customer-data {
  :name "The Firm Ltd"
  :business-id "1234567-8"})

(fact "new valid customer has the id"
  (customer/new valid-customer-data) => (contains {:_id anything}))

(fact "new valid customer has the data in it"
  (customer/new valid-customer-data) => (contains valid-customer-data))
