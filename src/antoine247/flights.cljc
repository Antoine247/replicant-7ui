(ns antoine247.flights)

(defn parse-date [s]
  (when (string? s)
    (when-let [[_ y m d] (re-find #"(\d\d\d\d).(\d\d).(\d\d)" s)]
      (str y "-" m "-" d))))

(defn format-inst [inst]
  (parse-date (pr-str inst)))

(defn prepare-departure-date [state]
  (cond-> {:value (or (::departure-date state) (format-inst (:now state)))}
    (::departure-date state)
    (assoc :invalid? (-> (::departure-date state)
                         parse-date
                         nil?))))

(defn prepare-return-date [state flight-type departure-date]
  (let [enabled? (= flight-type :roundtrip)]
    (cond-> {:value (or (::return-date state)
                        (:value departure-date))
             :disabled? (not enabled?)}
      (and enabled? (::return-date state))
      (assoc :invalid? (-> (::return-date state)
                           parse-date
                           nil?)))))

(defn before? [a b]
  (< (compare a b) 0))

(defn get-form-state [state]
  (let [departure-date (prepare-departure-date state)
        flight-type (or (::type state) :one-way)
        return-date (prepare-return-date state flight-type departure-date)]
    {::type flight-type
     ::departure-date departure-date
     ::return-date return-date
     ::button {:disabled? (or (:invalid? departure-date) 
                              (:invalid? return-date)
                              (and (= :roundtrip flight-type) (before? (:value return-date)
                                                                       (:value departure-date))))}}))

(comment
  (get-form-state {::return-date "2025-11-20"
                   ::departure-date "025-11-22"
                           ::type :roundtrip}))