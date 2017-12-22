(ns hours-service.customers.customer
  (:require
    [hours-service.util :as util]))

(defrecord Customer [_id name business-id])
(defrecord CustomerCreated [type data])
(defrecord CustomerCreationFailed [type data])

(defn new [customer-map]
  (map->Customer (util/with-id customer-map)))

(defn restore [customer-map]
  (map->Customer customer-map))

(defn new-created-event [customer]
  (->CustomerCreated "customer-created" customer))

(defn new-creation-failed-event [reason]
  (->CustomerCreationFailed "customer-creation-failed" reason))
