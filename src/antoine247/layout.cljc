(ns antoine247.layout)

(defn perform-action [_ _])

(defn tab-bar [current-view views]
  [:div.tabs.tabs-box {:role "tablist"}
   (for [{:keys [id text]} views]
     (let [current? (= id current-view)]
       [:a.tab (cond-> {:role "tab"}
                 current? (assoc :class "tab-active")
                 (not current?)
                 (assoc-in [:on :click]
                           [[:action/assoc-in [:current-view] id]]))
        text]))])