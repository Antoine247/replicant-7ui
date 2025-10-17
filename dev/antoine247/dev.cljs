(ns antoine247.dev
  (:require [antoine247.core :as core]))

(defn main []
  (println "loaded!"))

(defn ^:dev/after-load reload []
  (core/render-ui)
  (println "reloaded!!"))