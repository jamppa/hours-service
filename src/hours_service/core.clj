(ns hours-service.core
  (:require
    [hours-service.system :as system])
  (:gen-class))

(defn -main [& args]
  (println ";; Starting hours-service")
  (system/start (system/system-map)))
