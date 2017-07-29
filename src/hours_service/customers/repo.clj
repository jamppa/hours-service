(ns hours-service.customers.repo
  (:require
    [monger.collection :as mc]
    [monger.result :as mr]))

(def ^:private customer-collection "customers")

(defn- save-to-collection [db customer]
  (let [db (:db db)]
    (-> (mc/insert db customer-collection customer)
        (mr/acknowledged?))))

(defn save [db customer]
  (if (save-to-collection db customer) customer nil))
