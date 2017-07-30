(ns hours-service.customers.customer
  (:require
    [hours-service.util :as util]))


(defrecord Customer [_id name business-id])
(defrecord CustomerCreated [type data])
(defrecord CustomerCreationFailed [type data])


(defn new [customer-map]
  (map->Customer (util/with-id customer-map)))


(defn new-created [customer]
  (->CustomerCreated "customer-created" customer))


(defn new-creation-failed [customer]
  (->CustomerCreationFailed "customer-creation-failed" customer))
