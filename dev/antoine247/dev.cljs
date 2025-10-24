(ns antoine247.dev
  (:require [antoine247.core :as core]
            [hyperfiddle.rcf :as rcf]))

(rcf/enable!)


(defonce store (atom {:number 0}))
(defn main []
  (core/init store)
  (println "loaded!"))

(defn ^:dev/after-load reload []
  (core/init store)
  (println "reloaded!!"))