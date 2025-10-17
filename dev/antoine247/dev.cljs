(ns antoine247.dev)

(defn main []
  (println "loaded!"))

(defn ^:dev/after-load reload []
  (println "reloaded!!"))