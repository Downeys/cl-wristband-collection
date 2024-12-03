(ns app.core
  "This namespace contains your application and is the entrypoint for 'yarn start'."
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

;; event handlers
(def mockTrackData
  [{:id "1" :trackName "We Are the Egg" :bandName "Yetisburg" :audioSrc "#" :picSrc "https://wristbandaud.blob.core.windows.net/track-pics/yetisburg-ep.png" :buyLink "#"},
   {:id "2" :trackName "Apt 8" :bandName "Saturday Night Soldiers" :audioSrc "#" :picSrc "https://wristbandaud.blob.core.windows.net/track-pics/yetisburg-ep.png" :buyLink "#"},
   {:id "3" :trackName "Creole Funeral" :bandName "Saturday Night Soldiers" :audioSrc "#" :picSrc "https://wristbandaud.blob.core.windows.net/track-pics/yetisburg-ep.png" :buyLink "#"},
   {:id "4" :trackName "Come Up Away" :bandName "Yetisburg" :audioSrc "#" :picSrc "https://wristbandaud.blob.core.windows.net/track-pics/yetisburg-ep.png" :buyLink "#"},
   {:id "5" :trackName "Souls Coals" :bandName "Yetisburg" :audioSrc "#" :picSrc "https://wristbandaud.blob.core.windows.net/track-pics/yetisburg-ep.png" :buyLink "#"}])

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:playlist (conj (subvec 1 (- (count mockTrackData) 1)) (first mockTrackData))      
    :history [(first mockTrackData)]
    :status "paused"
    :songInPlayer "0"
    :songInFocus "0"}))

(rf/reg-event-db
 :next
 (fn [db [_ _]]
   (let [[first & remaining] (:playlist db)]
        (assoc db :history (conj history first) :playlist (conj remaining first))))
 
 (rf/reg-event-db
  :previous
  (fn [db [_ _]]
    (let [{:keys [playlist history]} db] 
      (assoc db :history (subvec history 0 (- (count history) 2)) :playlist (conj (last playlist) (subvec playlist 0 (- (count playlist) 2))))))

;; event dispatchers
(defn dispatch-timer-event
  []
  (let [now (js/Date.)]
    (rf/dispatch [:timer now])))

;; text components
(defn getTextColorClass [color?]
  (case color?
   "black" "text-black"
   "white" "text-white"
   "red" "text-red-600"
   "text-white"))

(defn getTextSize [size]
  (case size
    "sm" "text-sm"
    "md" "text-base"
    "lg" "text-lg"
    "xl" "text-xl"
    "2xl" "text-2xl"
    "3xl" "text-3xl"
    "4xl" "texl-4xl"))

(defn heading [text size additionalStyles?]
  [:h1 {:className (str "dark:text-white font-bold leading-5 " (getTextSize size) " " (when additionalStyles? additionalStyles?))} text])

(defn label [text size color additionalStyles?]
  [:p {:className (str (getTextSize size) " " (getTextColorClass color) " " (when additionalStyles? additionalStyles?))} text])

;; icons
(defn getColorHex [color]
  (case color
    "pink" "#FF00FF"
    "white" "#FFFFFF"
    "#FFFFFF"))

(defn playIcon [color size onClick]
    [:span {:onClick onClick}
     [:svg {:className (str (when (= size "big") "h-14 w-14") (when (= size "small") "h-7 w-7")) :viewBox "0 0 48 52" :fill "none" :xmlns "http://www.w3.org/2000/svg"}
      [:g {:filter "url(#filter0_d_106_4)"}
       [:path {:d "M4 0V44L44 22L4 0Z" :fill (getColorHex color)}]]
      [:defs
       [:filter {:id "filter0_d_106_4" :x "0" :y "0" :width "48" :height "52" :filterUnits "userSpaceOnUse" :color-interpolation-filters "sRGB"}
        [:feFlood {:flood-opacity "0" :result "BackgroundImageFix"}]
        [:feColorMatrix {:in "SourceAlpha" :type "matrix" :values "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" :result "hardAlpha"}]
        [:feOffset {:dy "4"}]
        [:feGaussianBlur {:stdDeviation "2"}]
        [:feComposite {:in2 "hardAlpha" :operator "out"}]
        [:feColorMatrix {:type "matrix" :values "0 0 0 0 1 0 0 0 0 0 0 0 0 0 1 0 0 0 0.25 0"}]
        [:feBlend {:mode "normal" :in2 "BackgroundImageFix" :result "effect1_dropShadow_106_4"}]
        [:feBlend {:mode "normal" :in "SourceGraphic" :in2 "effect1_dropShadow_106_4" :result "shape"}]]]]])

(defn pauseIcon [color size onClick]
  [:span {:onClick onClick}
   [:svg {:className (str (when (= size "big") "h-14 w-14") (when (= size "small") "h-7 w-7")) :viewBox "0 0 48 52" :fill "none" :xmlns "http://www.w3.org/2000/svg"} 
    [:path {:d "M0 0V40H13.3333V0H0ZM26.6667 0V40H40V0H26.6667Z" :fill (getColorHex color)}]]])

(defn nextIcon [onClick]
  [:span {:onClick onClick}
   [:svg.h-7.w-8 {:viewBox "0 0 33 27" :fill "none" :xmlns "http://www.w3.org/2000/svg"}
    [:g {:filter "url(#filter0_d_106_12)"}
     [:path {:d "M4 0V18.75L16.5 9.375L4 0ZM16.5 9.375V18.75L29 9.375L16.5 0V9.375Z" :fill "#06E7EC"}]]
    [:defs
     [:filter {:id "filter0_d_106_12" :x "0" :y "0" :width "33" :height "26.75" :filterUnits "userSpaceOnUse" :color-interpolation-filters "sRGB"}
      [:feFlood {:flood-opacity "0" :result "BackgroundImageFix"}]
      [:feColorMatrix {:in "SourceAlpha" :type "matrix" :values "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" :result "hardAlpha"}]
      [:feOffset {:dy "4"}]
      [:feGaussianBlur {:stdDeviation "2"}]
      [:feComposite {:in2 "hardAlpha" :operator "out"}]
      [:feColorMatrix {:type "matrix" :values "0 0 0 0 0.0235294 0 0 0 0 0.905882 0 0 0 0 0.92549 0 0 0 0.25 0"}]
      [:feBlend {:mode "normal" :in2 "BackgroundImageFix" :result "effect1_dropShadow_106_12"}]
      [:feBlend {:mode "normal" :in "SourceGraphic" :in2 "effect1_dropShadow_106_12" :result "shape"}]]]]])

(defn backIcon []
  [:span
   [:svg.h-7.w-8 {:viewBox "0 0 33 27" :fill "none" :xmlns "http://www.w3.org/2000/svg"}
    [:g {:filter "url(#filter0_d_106_15)"}
     [:path {:d "M16.5 0L4 9.375L16.5 18.75V0ZM16.5 9.375L29 18.75V0L16.5 9.375Z" :fill "#06E7EC"}]]
    [:defs
     [:filter {:id "filter0_d_106_15" :x "0" :y "0" :width "33" :height "26.75" :filterUnits "userSpaceOnUse" :color-interpolation-filters "sRGB"}
      [:feFlood {:flood-opacity "0" :result "BackgroundImageFix"}]
      [:feColorMatrix {:in "SourceAlpha" :type "matrix" :values "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 127 0" :result "hardAlpha"}]
      [:feOffset {:dy "4"}]
      [:feGaussianBlur {:stdDeviation "2"}]
      [:feComposite {:in2 "hardAlpha" :operator "out"}]
      [:feColorMatrix {:type "matrix" :values "0 0 0 0 0.0235294 0 0 0 0 0.905882 0 0 0 0 0.92549 0 0 0 0.25 0"}]
      [:feBlend {:mode "normal" :in2 "BackgroundImageFix" :result "effect1_dropShadow_106_15"}]
      [:feBlend {:mode "normal" :in "SourceGraphic" :in2 "effect1_dropShadow_106_15" :result "shape"}]]]]])

;; reusable componenets
(defn navButton [id url text]
  [:li.menu-item-container
   [:div.header-link-container
    [:a.header-link {:id id :type "button" :href url}
     [label text "2xl" "black"]]]])

(defn handlePlayButtonClick []
  (str "implement me"))

(defn playButton [size status]
  [:button {:className (str "flex flex-col items-center justify-center " (when (= size "big") "h-20 w-20 mx-12 rounded-full shadow-blue pl-3 pt-3") (when (= size "small") "h-14 w-14 border border-1 rounded-full pl-1 pt-1"))}
   (if (= status "playing")
     [playIcon (if (= size "big") "pink" "white") size handlePlayButtonClick]
     [pauseIcon (if (= size "big") "pink" "white") size handlePlayButtonClick])])

(defn nextButton [onClick]
  [:div.flex.flex-col.items-center.justify-center.h-10.w-10.rounded-full.shadow-pink.pt-2.pl-1 {:onClick onClick}
   [nextIcon]])

(defn backButton [onClick]
  [:div.flex.flex-col.items-center.justify-center.h-10.w-10.rounded-full.shadow-pink.pt-2.pr-1 {:onClick onClick}
   [backIcon]])

(defn track [playerStatus, trackInFocus, trackInPlayer, id, picSrc, bandName, trackName]
  [:div.flex.flex-row.h-22.w-full.justify-between.content-center.items-center.p-2 {:key id}
   [:div.flex
    [:div.h-14.w-14.justify-center.content-center
     [:img {:height 48 :width 48 :src picSrc :alt "Album Art"}]]
    [:div.flex-col.ml-2
     [label bandName "md" "white" "font-semibold"]
     [label trackName "md" "white"]]]
   [:div.flex.justify-center.content-center
    [playButton "small" playerStatus]]])

;; content componenets
(def navLinks
  [{:id "header-music-button" :url "https://wristbandradio.com" :text "Music"},
   {:id "header-submit-button" :url "https://wristbandradio.com/submit" :text "Submit"},
   {:id "header-about-button" :url "https://thewristbandcollection.com" :text "About"},
   {:id "header-contact-button" :url "https://wristbandradio.com/contact" :text "Contact"}])

(defn header []
  [:div.h-20.w-screen.bg-slate-950.flex.flex-row.justify-between.items-center.text-white.shadow-header.fixed.top-0.z-10
   [:div.flex.flex-row.items-center
    [:img {:height 75 :width 75 :src "/logo.png" :alt "logo"}]
    [heading "The Wristband Collection" "xl" "lg:text-4xl md:text-2xl font-primary"]] 
   [:input {:id "menu-toggle" :type "checkbox"}]
   [:label.menu-button-container {:for "menu-toggle"}
    [:div.menu-button]]
   [:ul.menu
    (map (fn [link]
           [navButton (:id link) (:url link) (:text link) {:key (:id link)}]) navLinks)]])

(defn smallPlayer []
  [:div.h-44.w-screen.flex.flex-col.pt-2.px-6.bg-slate-950.shadow-footer
   [:div.flex.flex-row.justify-between.items-center.mb-3
    [:div.flex.flex-row
     [label (str "BandName - TrackName") "lg" "white" "font-bold"]]]
   [:div.flex.flex-row.justify-center.items-center.p-2
    [backButton]
    [playButton "big" "playing"]
    [nextButton]]])

(defn homePage [tracks]
  [:main.flex.min-w-screen.min-h-screen.flex-col.px-12.pt-4.bg-slate-950.relative.top-20.z-0.font-secondary
   [:div
    (map (fn [t]
           [track "playing" "0" "0" "0" (:picSrc t) (:bandName t) (:trackName t)]) mockTrackData)
    [:div.h-40]]
   [:div.fixed.bottom-0.left-0
    [smallPlayer]]])

;; app components
(defn app []
  [:div
   [header]
   [homePage]])


(defn ^:dev/after-load render
  "Render the toplevel component for this app."
  []
  (r/render [app] (.getElementById js/document "app")))

(defn ^:export main
  "Run application startup logic."
  []
  (render))

