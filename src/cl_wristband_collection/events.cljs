(ns cl-wristband-collection.events
  (:require
   [re-frame.core :as re-frame]
   [cl-wristband-collection.db :as db]
   ))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))
