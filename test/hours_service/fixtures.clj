(ns hours-service.fixtures
  (:require [hours-service.customers.customer :as customer]))

(def valid-customer
  (customer/new {:name "Firma Oy Ab" :business-id "1234567-8"}))
