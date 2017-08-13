(ns hours-service.customers.repo-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.customers.customer :as customer]
    [hours-service.customers.repo :as repo]
    [hours-service.fixtures :as fixtures]
    [hours-service.system :as s]))

(background
  (before :facts (do (s/init-test) (s/start) (fixtures/load-fixtures (:db s/system))))
  (after :facts (s/stop)))


(def newly-customer (customer/new fixtures/valid-customer-data))
(fact "saves customer to repository"
  (repo/save (:db s/system) newly-customer) => newly-customer)


(fact "finds customer by its business id"
  (repo/find-by-business-id (:db s/system) "1234567-8")
    => fixtures/valid-customer)
