(ns limelight.production
  (:use
    [limelight.common]
    [limelight.theater]
    [limelight.stage-building :only (build-stages)]
    [limelight.prop-building :only (build-props)]
    [limelight.style-building :only (build-styles)]
    [limelight.scene :only (new-scene)]
    [limelight.util :only (read-src)])
  (:require
    [limelight.production-player]
    [limelight.core])
  (:import [limelight.theater Theater]))

(deftype Production [peer theater ns]

  limelight.model.api.ProductionProxy
  (send [this name args] nil)
  (getTheater [this] @theater)

  (illuminate [this]
    (let [player-path (resource-path this "production.clj")
          fs (limelight.Context/fs)
          player-src (if (.exists fs player-path) (.readTextFile fs player-path) nil)]
      (when player-src
        (binding [*ns* ns
                  limelight.production-player/*production* this]
          (use 'clojure.core)
          (use 'limelight.production-player)
          (read-src player-path player-src)))))

  (loadLibraries [this])

  (loadStages [this]
    (let [stages-path (resource-path this "stages.clj")
          fs (limelight.Context/fs)
          stages-src (if (.exists fs stages-path) (.readTextFile fs stages-path) nil)]
      (when stages-src
        (build-stages @theater stages-src stages-path))))

  (loadScene [this scene-path options]
    (let [fs (limelight.Context/fs)
          full-scene-path (resource-path this scene-path)
          options (zipmap (.keySet options) (.values options))
          options (assoc options :path full-scene-path :name (.filename fs full-scene-path))
          scene (new-scene options)
          _ (.setProduction @(.peer scene) peer)
          props-path (resource-path scene "props.clj")
          props-src (if (.exists fs props-path) (.readTextFile fs props-path) nil)]
      (when props-src
        (build-props scene props-src props-path))
      scene))

  (loadStyles [this path extendable-styles]
    (let [styles-path (str path "/styles.clj")
          fs (limelight.Context/fs)
          styles-src (if (.exists fs styles-path) (.readTextFile fs styles-path) nil)]
          (if styles-src
            (build-styles {} styles-src styles-path)
            {})))

  ResourceRoot
  (resource-path [this resource]
    (.pathTo (.getResourceLoader (.peer this)) resource)))

(defn new-production [peer]
  (let [ns (create-ns (gensym "limelight.dynamic-player.production-"))
        production (Production. peer (atom nil) ns)]
    (swap! (.theater production) (fn [old] (Theater. (.getTheater peer) production)))
    (.setProxy peer production)
    production))