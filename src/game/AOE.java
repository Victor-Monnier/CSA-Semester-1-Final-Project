package game;

import main.GamePanel;

import javafx.scene.shape.Rectangle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AOE {
    GamePanel gp;
    Player player;
    NPC npc;
    public BufferedImage image = null;
    public Rectangle hitBox;
    public int x = 0, y = 0, xOffset = 0, yOffset = 0, width, height, rotation, speed, spriteID = 5;
    public String ID, type, direction;
    public boolean alive = true;
    int timeLeft = 60;
    boolean canDespawn = true, isFriendly = false;
    double GS;

    //If attached to player, like meelee attack
    public AOE(GamePanel gp, Player player, int xOffset, int yOffset, int width, int height, int rotation, int speed, int timeLeft, String ID) {
        this.gp = gp;
        this.player = gp.player;
        this.GS = gp.GS;
        this.type = "player";
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.speed = speed;
        this.timeLeft = timeLeft;
        this.ID = ID;

        hitBox = new Rectangle(x, y, width, height);

        switch (this.ID) {
            case "melee":
                isFriendly = true;
                image = null;
                break;
        }
    }

    //If attached to NPC
    public AOE(GamePanel gp, NPC npc, int xOffset, int yOffset, int width, int height, int rotation, int speed, int timeLeft, int spriteID, String ID) {
        this.gp = gp;
        player = gp.player;
        this.npc = npc;
        this.GS = gp.GS;
        this.type = "npc";
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.speed = speed;
        this.timeLeft = timeLeft;
        this.ID = ID;
        this.spriteID = spriteID;

        image = gp.level.images[spriteID];

        hitBox = new Rectangle(x, y, width, height);

        switch (this.ID) {
            case "melee":
                isFriendly = false;
                image = null;
                break;
        }
    }

    //If existing freely
    public AOE(GamePanel gp, int x, int y, int width, int height, int rotation, int speed, int spriteID, String ID) {
        this.gp = gp;
        player = gp.player;
        this.GS = gp.GS;
        this.type = "free";
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        this.speed = speed;
        this.ID = ID;
        this.spriteID = spriteID;

        hitBox = new Rectangle(x, y, width, height);

        switch (this.ID) {
            case "nextLevel":
                canDespawn = false;
                image = null;
                break;       
        }
    }

    //Update methods for different types
    void updatePlayer() {
        switch (ID) {
            case "melee":
                x = (int) (Math.cos(Math.toRadians(gp.mouseAngle))*(player.weapon.x+player.weapon.width+xOffset+player.width/2)+player.x+player.width/2-width/2);
                y = (int) (-Math.sin(Math.toRadians(gp.mouseAngle))*(player.weapon.y+player.weapon.height+yOffset-height/2)+player.y+player.height/2-height/2);
                for (int npcIndex = 0; npcIndex < gp.level.npcs.size(); npcIndex++) {
                    if (main.CollisionChecker.checkCollision(hitBox, gp.level.npcs.get(npcIndex).hitBox)) {
                        gp.level.npcs.get(npcIndex).damage(20);
                    }
                }
                break;
        }

        //Updates hitbox for collisions
        hitBox.setX(x);
        hitBox.setY(y);

        //Time until it is removed
        if (canDespawn) {
            timeLeft--;
            if (timeLeft <= 0)
                alive = false;
        }
    }
    void updateNPC() {
        switch (ID) {
            case "melee":
                x = (int) (Math.cos(Math.toRadians(npc.playerAngle))*(npc.weapon.x+npc.weapon.width+xOffset+npc.width/2)+npc.x+npc.width/2-width/2);
                y = (int) (-Math.sin(Math.toRadians(npc.playerAngle))*(npc.weapon.y+npc.weapon.height+yOffset-height/2)+npc.y+npc.height/2-height/2);
                break;
        }

        //Updates hitbox for collisions
        hitBox.setX(x);
        hitBox.setY(y);

        //Time until it is removed
        if (canDespawn) {
            timeLeft--;
            if (timeLeft <= 0)
                alive = false;
        }

        if (main.CollisionChecker.checkCollision(hitBox, gp.player.hitBox )&& !isFriendly) {
            gp.player.damage(10);
        }
    }
    void updateFree() {
        switch (ID) {
            case "nextLevel":
                if (main.CollisionChecker.checkCollision(hitBox, gp.player.hitBox))
                    gp.createLevel(gp.levelID+1);
                break;
        }

        //Time until it is removed
        if (canDespawn) {
            timeLeft--;
            if (timeLeft <= 0)
                alive = false;
        }
    }
    
    //Calls update methods for specific type
    public void update() {
        switch (type) {
            case "player":
                updatePlayer();
                break;
            case "npc":
                updateNPC();
                break;
            case "free":
                updateFree();
                break;
        }
    }
    
    //Draws sprite
    public void draw (Graphics2D g2, double GS) {
        image = gp.level.images[spriteID];
        int apparentX = x-gp.player.x+gp.player.onScreenX, 
        apparentY = y-gp.player.y+gp.player.onScreenY, 
        apparentW = width, 
        apparentH = height;
        main.ImageUtils.drawRotatedImage(g2, image, -rotation, apparentX, apparentY, apparentW, apparentH, (int) (apparentX+apparentW/2), (int) (apparentY+apparentH/2), GS);
    }
}
