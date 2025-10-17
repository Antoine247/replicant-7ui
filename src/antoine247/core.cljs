(ns antoine247.core
  (:require [replicant.dom :as r]))

(defonce state (atom {:number 0}))

(swap! state assoc :number 2)

(defn render-ui []
  (r/render 
   js/document.body 
   [:div.m-8
    [:h1.text-lg "Counter"]
    [:div.flex.gap-4.items-center
     [:div "Number is " (:number @state)]
     [:button.btn "Count!"]]]))

(add-watch state ::render (fn [_ _ _ _] (render-ui)))