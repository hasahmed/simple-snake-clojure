(ns circle.core
  (:import (java.awt.event ActionListener KeyAdapter KeyEvent KeyListener WindowEvent)
           [javax.swing JFrame JPanel Timer]
           [java.awt Color Graphics Dimension Graphics2D]))

(def TICK 75)
(def state (atom {:x 150 :y 100 :dx 2 :dy 2 :dir {:up_down -1 :left_right -1} }))

(defn rand-color [] (Color/getHSBColor (rand) (rand) (rand)))



(defn make-panel []
  (proxy [JPanel] []
    (paintComponent [^Graphics2D g]
      (proxy-super paintComponent g)
      (let [{:keys [x y]} @state]
      (.setColor g Color/BLACK)
      (.fillOval g x y 200 200)))
    (getPreferredSize []
      (Dimension. 500 400))))

(defn -main [& _args]
  (let [frame (JFrame. "Black Circle") panel (make-panel)]
    (.setBackground panel Color/WHITE)
    (.add (.getContentPane frame) panel)
    (.pack frame)
    (.setDefaultCloseOperation frame JFrame/EXIT_ON_CLOSE)
    (.start (Timer. TICK (reify ActionListener
                 (actionPerformed [this actionEvent]
                   ; TODO: Implement gameloop
                   (swap! state (fn [{:keys [x y dx dy dir]}]
                      {:x (+ x (* dx (dir :left_right) )) :y (+ y dy)
                      :dx dx :dy dy :dir dir}))
                   (.repaint panel)
                   ;(.fillOval g 200 100 200 200)
                   ;(.setBackground panel (rand-color))
                   ;(.setColor g (rand-color))
                   ;(.fillOval g 150 100 200 200)
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
