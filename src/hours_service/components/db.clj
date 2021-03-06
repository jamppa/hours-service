(ns hours-service.components.db
  (:require
    [com.stuartsierra.component :as component]
    [monger.core :as mg]
    [monger.result :as mr]
    [monger.collection :as mc]))

(defrecord DB [env]
  component/Lifecycle

  (start [component]
    (println ";; Starting DB")
    (let [uri (get-in env [:config :db :url])
          {:keys [conn db]} (mg/connect-via-uri uri)]
      (assoc component :db db :conn conn)))

  (stop [component]
    (println ";; Stopping DB")
    (let [conn (:conn component)]
      (mg/disconnect conn)))
)


(defn new-db []
  (DB. nil))


(defn save [db coll obj]
  (let [db (:db db)]
    (-> (mc/insert db coll obj)
        (mr/acknowledged?))))


(defn save-all [db coll objs]
  (let [db (:db db)]
    (-> (mc/insert-batch db coll objs)
        (mr/acknowledged?))))


(defn remove-all [db coll]
  (let [db (:db db)]
    (mc/remove db coll)))


(defn find-one [db coll query]
  (let [db (:db db)]
    (mc/find-one-as-map db coll query)))
