(ns hours-service.customers.repo
  (:require
    [monger.collection :as mc]
    [monger.result :as mr]
    [hours-service.components.db :as DB]))

(def ^:private customer-collection "customers")

(defn- save-to-collection [db customer]
  (DB/save db customer-collection customer))

(defn save [db customer]
  (if (save-to-collection db customer) customer nil))
