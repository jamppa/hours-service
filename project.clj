(defproject hours-service "0.1.0-SNAPSHOT"
  :description "Hours Service - service for handling commands and queries of hours app"
  :url ""
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.5.1"]
                 [org.apache.kafka/kafka-clients "0.10.2.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [cheshire "5.7.0"]
                 [slingshot "0.12.2"]
                 [com.novemberain/monger "3.1.0"]
                 [environ "1.1.0"]]
  :main ^:skip-aot hours-service.core
  :target-path "target/%s"
  :profiles
  {
    :uberjar {:aot :all}
    :dev {:dependencies [[midje "1.9.0-alpha6"]]
          :plugins [[lein-midje "3.2.1"]]}
  })
