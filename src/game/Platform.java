package game;

import main.GamePanel;

import javafx.scene.shape.Rectangle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Platform {
    GamePanel gp;
    Level level;
    BufferedImage image;
    public Rectangle hitBox;
    public int x, y, width, height, xSpeed = 0, ySpeed = 0, spriteID, ID;
    public boolean alive = true;
    double GS;

    public Platform(GamePanel gp, Level level, int x, int y, int width, int height, int spriteID, int ID) {
        this.gp = gp;
        this.level = level;
        this.GS = gp.GS;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteID = spriteID;
        this.ID = ID;

        hitBox = new Rectangle(x, y, width, height);

        image = level.images[spriteID];
    }

    public void updateHitBox() {
        hitBox.setX(x);
        hitBox.setY(y);
        hitBox.setWidth(width);
        hitBox.setHeight(height);
    }

    public void update() {
        x += xSpeed;
        y += ySpeed;
        updateHitBox();
    }
    
    //Draws sprite
    public void draw (Graphics2D g2, double GS) {
        g2.drawImage(image, (int) ((x-gp.player.x+gp.player.onScreenX)*GS), (int) ((y-gp.player.y+gp.player.onScreenY)*GS), (int) (width*GS), (int) (height*GS), null);
    }
}

