(ns antoine247.temperatures-test
  (:require [hyperfiddle.rcf :as rcf]
            [antoine247.temperature :as sut]))


(rcf/tests
 "fahrenheit->celsius"
 (sut/fahrenheit->celsius 32) := 0
 (sut/fahrenheit->celsius 122) := 50
 (sut/fahrenheit->celsius 212) := 100)

(rcf/tests
 "celsius->fahrenheit"
 (sut/celsius->fahrenheit 0) := 32
 (sut/celsius->fahrenheit 50) := 122
 (sut/celsius->fahrenheit 100) := 212)

(rcf/tests
 "tests para la accion de setear una temperatura en el back"
 (sut/set-temperature {:celsius 50}) := [[:effect/assoc-in [:celsius] 50]
                                         [:effect/assoc-in [:fahrenheit] 122]]
 (sut/set-temperature {:fahrenheit 122}) := [[:effect/assoc-in [:celsius] 50]
                                         [:effect/assoc-in [:fahrenheit] 122]])


