(ns hours-service.customers.repo-test
  (:require
    [midje.sweet :refer :all]
    [hours-service.customers.customer :as customer]
    [hours-service.customers.repo :as repo]
    [hours-service.fixtures :as fixtures]
    [hours-service.system :as system]))

(def test-system (system/test-system-map))


(background (before :facts (alter-var-root #'test-system system/start))
            (after :facts (alter-var-root #'test-system system/stop)))


(fact "saves customer to repository"
  (repo/save (:db test-system) fixtures/valid-customer)
    => fixtures/valid-customer)
