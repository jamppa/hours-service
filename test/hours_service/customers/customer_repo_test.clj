(ns hours-service.customers.customer-repo-test
(:require
    [midje.sweet :refer :all]
    [hours-service.customers.customer :as customer]
    [hours-service.customers.customer-repo :as repo]
    [hours-service.fixtures :as fixtures]
    [hours-service.system :as s]))

(background
  (before :facts (do (s/init-test) (s/start) (fixtures/load-fixtures (:db s/system))))
  (after :facts (s/stop)))

(def newly-customer (customer/new fixtures/valid-customer-data))
(fact "saves customer to repository"
  (repo/save (:db s/system) newly-customer) => newly-customer)

(fact "throws when save fails to repository"
  (repo/save (:db s/system) newly-customer) => anything
  (repo/save (:db s/system) newly-customer) => (throws Exception))

(fact "finds customer by its business id"
  (repo/find-by-business-id (:db s/system) "1234567-8")
    => fixtures/valid-customer)

(fact "does not find non-existing customer by its business-id"
  (repo/find-by-business-id (:db s/system) "666666-6")
    => nil)

(fact "finds customer by its name"
  (repo/find-by-name (:db s/system) "Firma Oy Ab")
    => fixtures/valid-customer)

(fact "does not find non-existing customer by its name"
  (repo/find-by-name (:db s/system) "Some Weird Oy") => nil)