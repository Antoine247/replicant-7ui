(ns antoine247.flights-test
  (:require [hyperfiddle.rcf :as rcf]
            [antoine247.flights :as flights]))

(rcf/tests
 "Default para viajes de solo ida"
 (-> (flights/get-form-state {})
     ::flights/type) := :one-way

 "Usa un tipo viaje seleccionado"
 (-> (flights/get-form-state {::flights/type :roundtrip})
     ::flights/type) := :roundtrip

 "la fecha ida por defecto tiene que ser hoy"
 (-> (flights/get-form-state {:now #inst "2025-10-25"})
     ::flights/departure-date
     :value) := "2025-10-25"

 "enviando una fecha ida"
 (-> (flights/get-form-state {::flights/departure-date "2025-11-10"})
     ::flights/departure-date
     :value) := "2025-11-10"
 
  "ingresando una fecha de ida invalida"
 (-> (flights/get-form-state {::flights/departure-date "2025-11"})
     ::flights/departure-date
     :invalid?) := true

 "la fecha vuelta por defecto tiene que ser la de ida"
 (-> (flights/get-form-state {:now #inst "2025-10-25"
                              ::flights/departure-date "2025-11-10"})
     ::flights/return-date
     :value) := "2025-11-10"

 "enviando una fecha vuelta"
 (-> (flights/get-form-state {::flights/departure-date "2025-11-10"})
     ::flights/departure-date
     :value) := "2025-11-10"
 
 "la fecha vuelta debe estar desactivada por defecto"
            (-> (flights/get-form-state {:now #inst "2025-10-25"})
                ::flights/return-date
                :disabled?) := true
 
 "vuelo de vuelta esta activo solo si es un vuelo de ida y vuelta"
 (-> (flights/get-form-state {:now #inst "2025-10-25"
                              ::flights/type :roundtrip})
     ::flights/return-date
     :disabled?) := false
 
   "el boton debe estar desactivado si la fecha de ida es invalida"
 (-> (flights/get-form-state {::flights/departure-date "2025-11"})
     ::flights/button
     :disabled?) := true
 
 "ingresando una fecha de vuelta invalida"
 (-> (flights/get-form-state {::flights/return-date "2025-11"
                              ::flights/type :roundtrip})
     ::flights/return-date
     :invalid?) := true
            
 "ingresando una fecha de vuelta invalida"
 (-> (flights/get-form-state {::flights/return-date "2025-11"
                              ::flights/type :one-way})
     ::flights/return-date
     :invalid?) := nil       
 
 "el boton debe estar desactivado si la fecha de vuelta es invalida"
 (-> (flights/get-form-state {::flights/return-date "2025-11"
                              ::flights/type :roundtrip})
     ::flights/button
     :disabled?) := true
                 
  "el boton no debe estar desactivado si la fecha de vuelta es invalida en solo ida"
 (-> (flights/get-form-state {::flights/return-date "2025-11"
                              ::flights/type :one-way})
     ::flights/button
     :disabled?) := false

   "el boton debe estar desactivado si la fecha de vuelta es antes de la ida"
 (-> (flights/get-form-state {::flights/departure-date "2025-11-22"
                              ::flights/return-date "2025-11-20"
                              ::flights/type :roundtrip})
     ::flights/button
     :disabled?) := true
                
  "el boton no debe estar desactivado si la fecha de vuelta es antes de la ida y el tipo de buelo es ida"
  (-> (flights/get-form-state {::flights/departure-date "2025-11-22"
                               ::flights/return-date "2025-11-20"
                               ::flights/type :one-way})
      ::flights/button
      :disabled?) := false             
)


