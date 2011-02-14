;; gw666 github project sednaquery, file core.clj
;; see http://www.cfoster.net/articles/xqj-tutorial/simple-xquery.xml

;; Versions

;; 0.3 reads a set of v1.0 infocards in db named test
;;   NOTES: 1. reads from "doc('filename')" but not "collection('db-name')"
;;          2. seems to require ".xml" extension
;;          3. may not work with filnames containing spaces (unverified)

;; 0.2 implements SimpleQuery.java; commit aa28d6

(ns sednatest.core
  (:gen-class)
  (:import (javax.xml.xquery XQConnection XQDataSource
			     XQResultSequence)
	   (net.cfoster.sedna.xqj SednaXQDataSource)
	   (java.util Properties)))

(defn get-result
  ([result-sequence]
     (get-result result-sequence (vector)))
  ([result-sequence result-vector]
    ; (swank.core/break)
     (if (not  (.next result-sequence))
       result-vector
       (recur result-sequence (conj result-vector (.getItemAsString result-sequence (Properties.)))))))

;;insights taken from ~/tech/schemestuff/InfWb/main/sedna-utilities.ss
(defn -main []
  (let [xqs (SednaXQDataSource.)]
    (doto xqs
      (.setProperty "serverName" "localhost")
      (.setProperty "databaseName" "test"))
    (let [conn (.getConnection xqs "SYSTEM" "MANAGER")
	  xqe (.createExpression conn)
	  xqueryString
	  "declare default element namespace 'http://infoml.org/infomlFile';
for $card in doc('clinical-interview-v1_00.xml')/infomlFile/infoml[@cardId = 'gw667_113']
return $card/selectors/tag"
	  rs (.executeQuery xqe xqueryString)
	  result (get-result rs)]
      (prn result)
      (.close conn)
      (prn "Disconnected from Sedna"))))

  


