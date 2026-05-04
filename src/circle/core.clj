(ns circle.core
  (:import (java.awt.event ActionListener KeyAdapter KeyEvent KeyListener WindowEvent)
           [javax.swing JFrame JPanel Timer]
           [java.awt Color Graphics Dimension]))

(defn rand-color [] (Color/getHSBColor (rand) (rand) (rand)))


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
    (.start (Timer. 75 (reify ActionListener
                 (actionPerformed [this actionEvent]
                   :default
                 ))))
    (.addKeyListener frame (proxy [KeyAdapter] []
                       (keyPressed [keyEvent]
                         (if (= (.getKeyCode keyEvent) KeyEvent/VK_ESCAPE)
                           (.dispatchEvent frame (WindowEvent. frame WindowEvent/WINDOW_CLOSING)))
                         (if (= (.getKeyCode keyEvent) KeyEvent/VK_SPACE)
                           (.setBackground panel (rand-color)))
                         )))
    (.setLocationRelativeTo frame nil)
    (.setVisible frame true)))
