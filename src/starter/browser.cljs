(ns starter.browser)

;; start is called by init and after code reloading finishes
(defn ^:dev/after-load start []
  (js/console.log "start this thing")
  (js/alert "afterloading"))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (js/console.log "init")
  (start))

;; this is called before any code is reloaded
(defn ^:dev/before-load stop []
  (js/console.log "stop")
  (js/console.log "stop there")
  (js/console.log "asdasd"))
