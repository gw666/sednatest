;; this file implements SimpleQuery.java
;; see http://www.cfoster.net/articles/xqj-tutorial/simple-xquery.xml

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

(defn -main []
  (let [xqs (SednaXQDataSource.)]
    (doto xqs
      (.setProperty "serverName" "localhost")
      (.setProperty "databaseName" "test"))
    (let [conn (.getConnection xqs "SYSTEM" "MANAGER")
	  xqe (.createExpression conn)
	  xqueryString
	  "for $x in doc('books.xml')//book return $x/title/text()"
	  rs (.executeQuery xqe xqueryString)
	  result (get-result rs)]
      (prn result)
      (.close conn)
      (prn "Disconnected from Sedna"))))

  


