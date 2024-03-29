(ns lacinia-bug.main
  (:require
   [clojure.edn :as edn]
   [clojure.data.json :as json]
   [clojure.java.io :as io]
   [com.walmartlabs.lacinia :refer [execute]]
   [com.walmartlabs.lacinia.schema :as schema]
   [com.walmartlabs.lacinia.select-utils :as select-util]
   [com.walmartlabs.lacinia.parser.schema :as schema-parser]
   [com.walmartlabs.lacinia.util :refer [inject-resolvers]]))


;; The lacinia upgrade in 258706 resolved most of these.

;; There is one source of these errors left which I know about:

;; If a non-nullable field returns null, the null is propagated
;; upward. If that null is then merged with some sibling fragment
;; which is not null, this will throw with the exception "unable to
;; deep merge".

;; https://github.com/walmartlabs/lacinia/pull/452/commits/bfe8881c98003c0893ad4dbc6a15d6a253ccc21c

(defn load-schema
  [path]
  (schema/compile
   (schema-parser/parse-schema
    (slurp (io/resource path))
    {:resolvers {:Query {:findAllInEpisode
                         (fn [_ _ _]
                           [{:name "Luke" :episodes [{:name "A New Hope"}]}
                            {:name "Han" :episodes [{:name (select-util/->ResultTuple nil nil)}]}])}}})))

(def schema (load-schema "schema.graphql"))

(comment

  (load-schema "schema.graphql")

  )
