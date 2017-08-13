(ns hours-service.fixtures
  (:require
    [hours-service.customers.customer :as customer]
    [hours-service.components.db :as DB]))

(def valid-customer-data
  {:name "Firma Oy Ab" :business-id "1234567-8"})

(def valid-customer
  (customer/new valid-customer-data))

(def customers [valid-customer])

(defn- drop-all-collections [db]
  (doseq [coll ["customers"]]
    (DB/remove-all db coll)))

(defn load-fixtures [db]
  (drop-all-collections db)
  (DB/save-all db "customers" customers))
