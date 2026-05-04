(ns circle.core
  (:import (java.awt.event ActionListener KeyListener)
           [javax.swing JFrame JPanel Timer]
           [java.awt Color Graphics Dimension]))

(defn make-panel []
  (proxy [JPanel] []
    (paintComponent [^Graphics g]
      (proxy-super paintComponent g)
      (.setColor g Color/BLACK)
      (.fillOval g 150 100 200 200))
    (getPreferredSize []
      (Dimension. 500 400))))

(defn -main [& _args]
  (let [frame (JFrame. "Black Circle")
        panel (make-panel)]
    (.setBackground panel Color/WHITE)
    (.add (.getContentPane frame) panel)
    (.pack frame)
    (print "Hello")
    (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
    (.start (Timer. 75 (reify ActionListener
                 (actionPerformed [this actionEvent]
                   ;(println "Goodbye")
                   ;(println this)
                   ;(println actionEvent)
                 ))))
    (.addKeyListener frame (reify KeyListener
                       (keyPressed [this keyEvent]
                         (println keyEvent)
                         )
                       (keyReleased [this keyEvent]
                         ;(println keyEvent)
                         )
                       (keyTyped [this keyEvent]
                         :default
                         ;(println keyEvent)
                         )
                             ))
    ;(.dispatchEvent frame (WindowEvent. frame WindowEvent/WINDOW_CLOSING))

    (.setLocationRelativeTo frame nil)
    (.setVisible frame true)))
