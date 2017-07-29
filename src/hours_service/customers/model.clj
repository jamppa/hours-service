(ns hours-service.customers.model
  (:require [hours-service.util :as util]))

(defrecord Customer [id name business-id])

(defn new [customer-map]
  (map->Customer (util/with-id customer-map)))
