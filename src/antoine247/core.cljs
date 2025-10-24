(ns antoine247.core
  (:require [replicant.dom :as r]
            [antoine247.counter :as counter]
            [antoine247.layout :as layout]
            [antoine247.temperature :as temp]
            [clojure.walk :as walk]))

(def views
  [{:id :counter
    :text "Counter"}
   {:id :temperatures
    :text "Temperatures"}])

(defn get-current-view [state]
   (:current-view state))

(defn process-effect [store [effect & args]]
  (case effect
    :effect/assoc-in
    (apply swap! store assoc-in args)))

(defn render-ui [state]
  (let [current-view (get-current-view state)]
    [:div.m-8
     (layout/tab-bar (get-current-view state)  views)
     [:div.m-8
      (case current-view
        :counter
        (counter/render-ui state)

        :temperatures
        (temp/render-ui state)
        
        [:div.m-8 [:h1.text-lg "Select your UI of choice"]])]]))

(defn perform-actions [state event-data]
  (mapcat
   (fn [action]
     (prn (first action) (rest action))
     (or (counter/perform-action state action)
         (layout/perform-action state action)
         (temp/perform-action state action)
         (case (first action)
           :action/assoc-in
           [(into [:effect/assoc-in] (rest action))]
           
           (prn "Unknown action"))))
   event-data))

(defn interpolate [event data]
  (walk/postwalk
   (fn [x]
     (case x
       :event.target/value-as-number (some-> event .-target .-valueAsNumber)
       x))
   data))

(defn init [store] 
  (add-watch store ::render (fn [_ _ _ new-state]
                              (r/render
                               js/document.body
                               (render-ui new-state))))
  
  
  
  (r/set-dispatch!
   (fn [{:replicant/keys [dom-event]} event-data]
     (js/console.log dom-event)
     (->> (interpolate dom-event event-data)
          (perform-actions @store)
          (run! #(process-effect store %)))))
  
  
  (swap! store assoc ::loaded-at (.getTime (js/Date.))))