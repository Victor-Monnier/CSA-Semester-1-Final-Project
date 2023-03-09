package game;

import java.awt.image.BufferedImage;

import javafx.scene.shape.Rectangle;

public class Entity {
    public int x = 0, y = 0, width, height, speed, deltaX, deltaY, health, maxHealth, dmgCoolDown, attackCoolDown, fireDuration;
    public boolean alive;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public Rectangle hitBox;

    //Used for animations
    public int spriteCounter = 0;
    public int spriteNum = 1;
}
