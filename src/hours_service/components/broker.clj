(ns hours-service.components.broker
  (:require
    [com.stuartsierra.component :as component]
    [cheshire.core :as cheshire])
  (:import
    [org.apache.kafka.clients.producer KafkaProducer ProducerRecord]
    [org.apache.kafka.clients.consumer KafkaConsumer ConsumerRecord]))

(def ^:private pending-cmds-topic "pending-cmds")
(def ^:private succeed-cmds-topic "succeed-cmds")
(def ^:private failed-cmds-topic "failed-cmds")

(defn- new-producer [conf]
  (KafkaProducer. conf))

(defn- new-consumer [conf]
  (doto (KafkaConsumer. conf)
    (.subscribe [pending-cmds-topic])))

(defn- new-producer-record [topic message]
  (ProducerRecord. topic message))

(defrecord Broker [env]
  component/Lifecycle

  (start [component]
    (println ";; Starting Broker")
    (assoc component
      :producer (new-producer (get-in env [:config :broker-producer]))
      :consumer (new-consumer (get-in env [:config :broker-consumer]))))

  (stop [component]
    (println ";; Stopping Broker")
    (let [producer (:producer component)
          consumer (:consumer component)]
      (.close producer)
      (.close consumer))
    (assoc component
      :producer nil
      :consumer nil))
)

(defn new-broker []
  (Broker. nil))

(defn send-successful-event [broker event]
  (let [producer (:producer broker)
        event-json (cheshire/generate-string event)]
    (.send producer (new-producer-record succeed-cmds-topic event-json))))

(defn send-failed-event [broker event]
  (let [producer (:producer broker)
        event-json (cheshire/generate-string event)]
    (.send producer (new-producer-record failed-cmds-topic event-json))))

(defn poll [broker handler]
  (while true
    (let [records (.poll (:consumer broker) 100)]
      (doseq [record records]
        (handler record)))))
