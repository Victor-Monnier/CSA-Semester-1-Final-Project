package game;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Particle {
    public int x, y, width, height, rotation, deltaX, deltaY, timeLeft = 60;
    public boolean alive = true;
    public String ID;
    BufferedImage image;
    GamePanel gp;

    public Particle (GamePanel gp, int x, int y, int rotation, String ID) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.ID = ID;

        switch (this.ID) {
            case "fire":
                width = (int) (Math.random()*13+8);
                height = width;
                this.rotation = (int) (Math.random()*9)*45;
                deltaY = (int) -(Math.random()*3+1);
                timeLeft = (int) (Math.random()*61+60);
                image = gp.particle_1;
        }
    }

    public void update() {
        switch (ID) {
            case "fire":
                deltaX = (int) (Math.random()*5-2);
                x += deltaX;
                y += deltaY;
                if (Math.random() < 0.1) {
                    width--;
                    height--;
                    this.rotation = (int) (Math.random()*9)*45;
                }
                if (width <= 0 || height <= 0)
                    alive = false;
                break;
        }

        if (--timeLeft <= 0 || x > gp.level.levelWidth || x < 0 || y > gp.level.levelHeight || y < 0)
            alive = false;
    }

    //Draws sprite
    public void draw (Graphics2D g2, double GS) {
        main.ImageUtils.drawRotatedImage(g2, image, rotation, x-gp.player.x+gp.player.onScreenX, y-gp.player.y+gp.player.onScreenY, width, height, x-gp.player.x+gp.player.onScreenX+width/2, y-gp.player.y+gp.player.onScreenY+height/2, GS);
    }
}