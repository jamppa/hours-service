(ns hours-service.util
  (:import [org.bson.types ObjectId]))

(defn with-id [m]
  (merge m {:_id (ObjectId.)}))
