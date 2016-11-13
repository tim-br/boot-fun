(set-env!
 :source-paths #{"src"})

#_(require '[boot.pod :as pod]
         '[boot.util :as util])

(task-options!
 pom {:project 'top-secret
      :version "0.1.0"}
 aot {:namespace '#{core}}
 jar {:main 'core
      :file "fun.jar"
      :manifest {"Description" "topsecret web application"}})

; (deftask compile-java
;   []
;   (set-env!
;    :source-paths #{"java_code"})
;   (comp
;     (javac)
;     (show :fileset true)))

(deftask compile-java
  []
  (with-pre-wrap fs
    (let [t (tmp-dir!)]
      (println (input-files fs))
      (println "*****")
      ;(show :fileset true)
      (doseq [lc-tmp (input-files fs)]
        (println (count (input-files fs)))
        (let [lc-file (tmp-file lc-tmp)]
          (-> fs (add-resource lc-file) commit!)
          (println (slurp lc-file))
          (println "check")
          (println lc-file))))
  fs))

  #_(comp
    (show :fileset true))

(deftask fun-t
  []
  (set-env!
   :source-paths #{"java_code"})
  (comp
    (compile-java)
    (show :fileset true)
    (aot)
    (pom)
    (uber)
    (jar)
    (target)))

(deftask cj
  []
  (comp
    (compile-java)
    ;(show :fileset true)
    (target)))

(deftask mytest
  []
  (comp
    (compile-java)
    (show :classpath true)
    (aot)
    (pom)
    (uber)
    (jar)
    (target)))

; (deftask assoc-compile-java
;   []
;   (with-pre-wrap fs
;     (let [t (tmp-dir!)]
;       (spit (clojure.java.io/file t "version.txt") version)
;       (-> fs (add-resource t) commit!))))

(deftask build
  "Build uberjar"
  []
  (comp
    (aot)
    (pom)
    (uber)
    (jar)
    (target)))
