;; this file implements ConnectAndDisconnect.java
;; see http://www.cfoster.net/articles/xqj-tutorial/simple-xquery.xml

(ns sednatest.core
  (:gen-class)
  (:import (javax.xml.xquery XQConnection XQDataSource)
	   (net.cfoster.sedna.xqj SednaXQDataSource)))

(defn -main []
  (let [xqs (SednaXQDataSource.)]
    (doto xqs
      (.setProperty "serverName" "localhost")
      (.setProperty "databaseName" "test"))
    (let [conn (.getConnection xqs "SYSTEM" "MANAGER")]
      (prn "Connected to Sedna")
      (prn "Disconnected from Sedna"))))


