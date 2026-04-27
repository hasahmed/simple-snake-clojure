(ns circle.core
  (:import [javax.swing JFrame JPanel]
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
    (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
    (.setLocationRelativeTo frame nil)
    (.setVisible frame true)))
