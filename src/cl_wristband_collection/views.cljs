(ns cl-wristband-collection.views
  (:require
   [re-frame.core :as re-frame]
   [cl-wristband-collection.events :as events]
   [cl-wristband-collection.subs :as subs]
   ))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1
      "Hello from " @name]
     ]))
