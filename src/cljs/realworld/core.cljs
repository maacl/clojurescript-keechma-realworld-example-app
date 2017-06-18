(ns realworld.core
  (:require [reagent.core :as reagent]
            [keechma.app-state :as app-state]
            [realworld.controllers :refer [controllers]]
            [realworld.ui :refer [ui]]))

(def app-definition
  {:components    ui
   :controllers   controllers 
   :subscriptions {}
   :routes [["" {:page "home"}]
            ":page"]
   :html-element  (.getElementById js/document "app")})

(defonce running-app (clojure.core/atom))

(defn start-app! []
  (reset! running-app (app-state/start! app-definition)))

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!)
    (println "dev mode")))

(defn reload []
  (let [current @running-app]
    (if current
      (app-state/stop! current start-app!)
      (start-app!))))

(defn ^:export main []
  (dev-setup)
  (start-app!))
