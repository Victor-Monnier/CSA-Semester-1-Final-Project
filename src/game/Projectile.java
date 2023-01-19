package game;

import main.GamePanel;

import javafx.scene.shape.Rectangle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Projectile {
    GamePanel gp;
    public BufferedImage image;
    public Rectangle hitBox;
    public int x, y, width, height, rotation, speed, spriteID;
    private double trueX, trueY;
    public boolean alive = true, isPlayerProjectile;
    public String ID;
    double GS;

    public Projectile(GamePanel gp,int x, int y, int width, int height, int rotation, int speed, boolean isPlayerProjectile, String ID) {
        this.gp = gp;
        this.GS = gp.GS;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.speed = speed;
        this.isPlayerProjectile = isPlayerProjectile;
        this.ID = ID;
        trueX = x;
        trueY= y;

        hitBox = new Rectangle(x, y, width, height);

        switch (ID) {
            case "magic":
                image = gp.projectile_1;
                break;
            case "fire":
                image = null;
                break;
            case "power":
                image = gp.projectile_2;
                break;
            case "life-steal":
                image = gp.projectile_3;
                break;
        }
    }

    //Updates position & rotation. Checks for collisions.
    public void update() {
        double rotationRadians = Math.toRadians(rotation);
        //TrueX x and y pos based on speed and angle
        trueX += Math.cos(rotationRadians)*speed;
        trueY -= Math.sin(rotationRadians)*speed;
        x = (int) trueX;
        y = (int) trueY;

        //Updates hitbox for collisions
        hitBox.setX(x);
        hitBox.setY(y);

        //Particle specific
        switch (ID) {
            case "fire":
                if (gp.level.time % 2 == 0)
                    gp.level.particles.add(new Particle(gp, (int) (Math.random()*(width/2)+x+width/4), (int) (Math.random()*(height/2)+y+height/4), 0, "fire"));
                break;
            case "power":
                if (gp.level.time % 20 < 10)
                    rotation++;
                else
                    rotation--;
                if (speed <= 0)
                    alive = false;
                break;
        }

        if (main.CollisionChecker.checkCollision(hitBox, gp.player.hitBox) && !isPlayerProjectile) {
            switch (ID) {
                case "magic":
                    gp.player.damage(10);
                    alive = false;
                    break;
                case "fire":
                    gp.player.fireDuration += 11;
                    break;
                case "power":
                    gp.player.damage(25);
                    break;
            }
        }
        for (int npcIndex = 0; npcIndex < gp.level.npcs.size(); npcIndex++) {
            if (main.CollisionChecker.checkCollision(hitBox, gp.level.npcs.get(npcIndex).hitBox) && isPlayerProjectile) {
                switch (ID) {
                    case "magic":
                        gp.level.npcs.get(npcIndex).damage(1000);
                        alive = false;
                        break;
                    case "fire":
                        gp.level.npcs.get(npcIndex).fireDuration += 6;
                        break;
                    case "power":
                        gp.level.npcs.get(npcIndex).damage(49);;
                        break;
                    case "life-steal":
                        if (Math.random() > 0.90)
                            gp.player.heal(1);
                        else
                            gp.level.npcs.get(npcIndex).damage(2);
                        alive = false;
                        break;
                }
            }
        }
        for (int platformIndex = 0; platformIndex < gp.level.platforms.size(); platformIndex++) {
            if (main.CollisionChecker.checkCollision(hitBox, gp.level.platforms.get(platformIndex).hitBox)) {
                alive = false;
            }
        }
        if (x > gp.level.levelWidth || x < 0 || y > gp.level.levelHeight || y < 0)
            alive = false;
    }
    
    //Draws sprite
    public void draw (Graphics2D g2, double GS) {
        int apparentX = x-gp.player.x+gp.player.onScreenX, 
        apparentY = y-gp.player.y+gp.player.onScreenY, 
        apparentW = width, 
        apparentH = height;
        main.ImageUtils.drawRotatedImage(g2, image, -rotation, apparentX, apparentY, apparentW, apparentH, (int) (apparentX+apparentW/2), (int) (apparentY+apparentH/2), GS);
    }
}
