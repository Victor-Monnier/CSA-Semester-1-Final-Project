package main;

import javafx.scene.shape.Rectangle;

public class CollisionChecker {
    //Returms true if both rectangles collide
    public static boolean checkCollision (Rectangle a, Rectangle b) {
        return a.intersects(b.getBoundsInLocal());
    }
}
