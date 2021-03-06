(ns hours-service.customers.customer-repo
  (:require
    [monger.collection :as mc]
    [monger.result :as mr]
    [hours-service.components.db :as DB]
    [hours-service.customers.customer :as customer]
    [slingshot.slingshot :as s]))

(def ^:private customer-collection "customers")

(defn- save-to-collection [db customer]
  (DB/save db customer-collection customer))

(defn- find-one-from-collection [db query]
  (DB/find-one db customer-collection query))

(defn save [db customer]
  (if (save-to-collection db customer)
    customer
    (s/throw+ {:type ::customer-repo-failure :customer customer})))

(defn find-by-business-id [db id]
  (when-let [c-map (find-one-from-collection db {:business-id id})]
    (customer/restore c-map)))

(defn find-by-name [db name]
  (when-let [c-map (find-one-from-collection db {:name name})]
    (customer/restore c-map)))