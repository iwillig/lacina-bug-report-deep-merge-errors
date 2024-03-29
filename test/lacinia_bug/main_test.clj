(ns lacinia-bug.main-test
  (:require
   [lacinia-bug.main :as main]
   [com.walmartlabs.lacinia :refer [execute]]
   [flatland.ordered.map :as ordered]
   [com.walmartlabs.lacinia.internal-utils :as lacinia-tools]
   [clojure.test :refer [deftest is]]
   [clojure.java.io :as io]))

(defn q
  [query-string vars]
  (execute main/schema query-string vars {}))


(deftest test-graphql-schema
  (let [query-string (slurp (io/resource "test_query.graphql"))]


    (is (= ""
           (q query-string {})))))



(deftest test-error---deep-merge

  (is (= {}
         (lacinia-tools/deep-merge nil #ordered/map
                                   ([:id "v2:sp:5390b4a4-5b91-4e27-b80b-879e2762bf69:197655"]
                                    [:publicId 197655]
                                    [:name "Marius Needs QA"]
                                    [:__typename :Space]))))

  )
