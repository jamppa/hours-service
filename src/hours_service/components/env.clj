(ns hours-service.components.env
  (:require [com.stuartsierra.component :as component]))

(def ^:private default-config {
  :broker-producer {
    "bootstrap.servers" "broker:9092"
    "acks" "all"
    "retries" (int 0)
    "batch.size" (int 16384)
    "linger.ms" (int 1)
    "key.serializer" "org.apache.kafka.common.serialization.StringSerializer"
    "value.serializer" "org.apache.kafka.common.serialization.StringSerializer"
  }
  :broker-consumer {
    "bootstrap.servers" "broker:9092"
    "group.id" "hours-service"
    "enable.auto.commit" "true"
    "key.deserializer" "org.apache.kafka.common.serialization.StringDeserializer"
    "value.deserializer" "org.apache.kafka.common.serialization.StringDeserializer"
  }
  :db {
    :host "db"
    :port 27017
  }
})

(defrecord Env [config]
  component/Lifecycle

  (start [component]
    (println ";; starting Env")
    (merge component {:config config}))

  (stop [component]
    (println ";; stopping Env")
    (merge component {:config nil}))
)

(defn new-env
  ([conf]
    (Env. conf))
  ([]
    (Env. default-config)))
