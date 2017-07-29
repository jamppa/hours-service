(ns hours-service.customers.customer
  (:require
    [hours-service.util :as util]))


(defrecord Customer [_id name business-id])


(defn new [customer-map]
  (map->Customer (util/with-id customer-map)))
