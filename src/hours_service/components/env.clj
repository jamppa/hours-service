(ns hours-service.components.env
  (:require
    [com.stuartsierra.component :as component]
    [environ.core :refer [env]]))

(def ^:private broker-host
  (env :broker-host))

(def ^:private db-url
  (env :db-url))

(def ^:private default-config {
  :broker-producer {
    "bootstrap.servers" broker-host
    "acks" "all"
    "retries" (int 0)
    "batch.size" (int 16384)
    "linger.ms" (int 1)
    "key.serializer" "org.apache.kafka.common.serialization.StringSerializer"
    "value.serializer" "org.apache.kafka.common.serialization.StringSerializer"
  }
  :broker-consumer {
    "bootstrap.servers" broker-host
    "group.id" "hours-service"
    "enable.auto.commit" "true"
    "key.deserializer" "org.apache.kafka.common.serialization.StringDeserializer"
    "value.deserializer" "org.apache.kafka.common.serialization.StringDeserializer"
  }
  :db {
    :url db-url
  }
})

(defrecord Env [config]
  component/Lifecycle

  (start [component]
    (println ";; starting Env")
    (println default-config)
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
