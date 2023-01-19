package game;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public class NPC extends Entity {
    public String ID, behaviorID, appearanceStyle, direction = "left";
    public int playerAngle, rotation, moneyOnDeath;
    public double sprayFactor;
    public boolean alive = true, onGround = false, canMove = false, showWeapon = false;
    private int[] spritesIDs, rotationRange = {0, 360};
    GamePanel gp;
    Weapon weapon;
    
    //Creates NPC largely based on ID. spriteIDs is an array containing the indices of the sprites used
    public NPC(GamePanel gp, int x, int y, String ID, int[] spritesIDs) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.spritesIDs = spritesIDs;

        switch (ID) {
            case "meleeEnemy1":
                weapon = new Weapon(gp, "enemy_stick_1");
                width = 80;
                height = 80;
                speed = 8;
                health = 100;
                sprayFactor = 10;
                moneyOnDeath = 20;
                behaviorID = "agressiveMelee";
                appearanceStyle = "standard";
                canMove = true;
                showWeapon = true;
                break;
            case "meleeEnemy2":
                weapon = new Weapon(gp, "enemy_stick_2");
                width = 200;
                height = 200;
                speed = 5;
                health = 500;
                sprayFactor = 10;
                moneyOnDeath = 50;
                behaviorID = "agressiveMelee";
                appearanceStyle = "standard";
                canMove = true;
                showWeapon = true;
                break;
            case "turret1":
                weapon = new Weapon(gp, "enemy_magic_stick");
                width = 100;
                height = 100;
                speed = 0;
                health = 100;
                sprayFactor = 10;
                moneyOnDeath = 10;
                behaviorID = "agressiveRanged";
                appearanceStyle = "turret";
                canMove = false;
                showWeapon = false;
                rotationRange[0] = 0;
                rotationRange[1] = 270;
                break;
            case "turret2":
                weapon = new Weapon(gp, "enemy_magic_stick");
                width = 100;
                height = 100;
                speed = 0;
                health = 100;
                sprayFactor = 10;
                moneyOnDeath = 5;
                behaviorID = "agressiveRanged";
                appearanceStyle = "turret";
                canMove = false;
                showWeapon = false;
                rotationRange[0] = 90;
                rotationRange[1] = 180;
                break;
            case "turret3":
                weapon = new Weapon(gp, "enemy_fire_stick");
                width = 100;
                height = 100;
                speed = 0;
                health = 100;
                sprayFactor = 10;
                moneyOnDeath = 25;
                behaviorID = "agressiveRanged";
                appearanceStyle = "turret";
                canMove = false;
                showWeapon = false;
                rotationRange[0] = 180;
                rotationRange[1] = 360;
                break;
            case "turret4":
                weapon = new Weapon(gp, "enemy_magic_stick");
                width = 100;
                height = 100;
                speed = 0;
                health = 100;
                sprayFactor = 10;
                moneyOnDeath = 5;
                behaviorID = "agressiveRanged";
                appearanceStyle = "turret";
                canMove = false;
                showWeapon = false;
                rotationRange[0] = 0;
                rotationRange[1] = 360;
                break;
            case "turret5":
                weapon = new Weapon(gp, "enemy_fire_stick");
                width = 100;
                height = 100;
                speed = 0;
                health = 100;
                sprayFactor = 10;
                moneyOnDeath = 25;
                behaviorID = "agressiveRanged";
                appearanceStyle = "turret";
                canMove = false;
                showWeapon = false;
                rotationRange[0] = 0;
                rotationRange[1] = 360;
                break;
        }

        //Hit box for collisions
        hitBox = new Rectangle(x, y, width, height);
    }

    //Returns input in the form of [jump/up, left, right]
    boolean[] movement() {
        boolean[] mov = 
            {false,
            false,
            false};
        boolean holeToLeft = false, holeToRight = false, wallToLeft = false, wallToRight = false;

        for (int i = 0; i < gp.level.platforms.size(); i++) {
            Platform thisPlatform = gp.level.platforms.get(i);

            //Hole to left
            hitBox.setWidth(1); hitBox.setHeight(1);
            hitBox.setX(x-10); hitBox.setY(y+height+1);
            if (!main.CollisionChecker.checkCollision(hitBox, thisPlatform.hitBox) && onGround)
                holeToLeft = true;

            //Hole to right
            hitBox.setX(x+width+10);
            if (!main.CollisionChecker.checkCollision(hitBox, thisPlatform.hitBox) && onGround)
                holeToRight = true;

            //Wall to left
            hitBox.setX(x-1); hitBox.setY(y);
            if (main.CollisionChecker.checkCollision(hitBox, thisPlatform.hitBox))
                wallToLeft = true;

            //Wall to right
            hitBox.setX(x+width+1);
            if (main.CollisionChecker.checkCollision(hitBox, thisPlatform.hitBox))
                wallToRight = true;
        }

        switch (behaviorID) {
            case "agressiveMelee":
                mov[0] = gp.player.y+gp.player.height < y+height; //If player is above npc
                //Left if on ground and left AND jump if there is a hole
                if (gp.player.x+gp.player.width < x-weapon.width/4 && !wallToLeft) {
                    mov[1] = true;
                    if (holeToLeft)
                        mov[0] = true;
                }
                //Right if on ground and Right AND jump if there is a hole
                if (gp.player.x > x+width+weapon.width/4 && !wallToRight) {
                    mov[2] = true;
                    if (holeToRight)
                        mov[0] = true;
                }
                break;
        }

        //Resets hit box
        hitBox.setWidth(width); hitBox.setHeight(height);
        hitBox.setX(x); hitBox.setY(y);

        return mov;
    }

    //Does a set amount of damage
    public void damage(int dmg) {
        if (dmgCoolDown == 0) {
            health -= dmg;
            dmgCoolDown = 6;
        }
    }

    //Creates projectiles based on weapon used
    void shoot() {
        if (behaviorID.contains("agressive"))
            for (Projectile projectile : weapon.getProjectiles(this))
                gp.level.projectiles.add(projectile);
        attackCoolDown = weapon.attackCoolDown;
    }
    
    //Damaging areas of effect are created around weapon
    void melee() {
        if (behaviorID.contains("agressive")) {
            if (attackCoolDown == 0) {
                if (gp.getGameState().equals("PLAYING")) {
                    gp.level.aoes.add(new AOE(gp, this, 0, 0, 30, 30, 0, 0, 5, 5, "melee"));
                    gp.level.aoes.add(new AOE(gp, this, -weapon.width/2, -weapon.height/2, 30, 30, 0, 0, 5, 5, "melee"));
                    gp.level.aoes.add(new AOE(gp, this, -weapon.width, -weapon.height, 30, 30, 0, 0, 5, 5, "melee"));
                    attackCoolDown = weapon.attackCoolDown;
                }
            }
        }
    }
    
    //Logical updates
    public void update() {
        if (health <= 0) {
            alive = false;
            gp.player.inventory.money += moneyOnDeath;
            gp.playSF(1);
        }
        if (fireDuration > 0) {
            if (gp.level.time % 6 == 0)
                health--;
            if (gp.level.time % 2 == 0)
                gp.level.particles.add(new Particle(gp, (int) (Math.random()*(width/2)+x+width/4), (int) (Math.random()*(height/2)+y+height/4), 0, "fire"));
        }

        boolean isJumping = false;
        onGround = false;
        int xMovement = 0, yMovement = 0;
        ArrayList<Platform> platforms = gp.level.platforms;
        Rectangle platformHitBox;

        if (canMove) {
            boolean[] NPCMovement = movement();
            if (NPCMovement[0]) {
                isJumping = true;
                direction = "up";
            }
            if (NPCMovement[1]) {
                deltaX -= 1;
                direction = "left";
            }
            if (NPCMovement[2]) {
                deltaX += 1;
                direction = "right";
            }

            //Calculates the x amount of change
            if (deltaY > -30) {
                deltaY--;
            }
            yMovement = -(deltaY/2);
            //Prepares player for y checks
            hitBox.setY(y+yMovement);  hitBox.setWidth(width-2);  hitBox.setX(x+1);
            boolean hitObstacle = false;
            //ALL Y CHECKS
            for (int platformIndex = 0; platformIndex < platforms.size(); platformIndex++) {
                platformHitBox = platforms.get(platformIndex).hitBox;
                //What to do if can't move y
                if (main.CollisionChecker.checkCollision(hitBox, platformHitBox)) {
                    hitObstacle = true;
                    deltaY = -1; //Loses speed when bonking head and makes sure momentum doesn't build up while grounded
                    //If hitting floor
                    if (yMovement > 0) {
                        onGround = true;
                        y = (int) (platformHitBox.getY()-height);
                        //Allow jumping
                        if (isJumping) {
                            deltaY = 27;
                        }
                    }
                    //If hitting ceiling
                    else {
                        y = (int) (platformHitBox.getY()+platformHitBox.getHeight());
                    }
                }
            }
            //Updates positions after checks
            if (!hitObstacle) {
                y += yMovement;
            }
            //Hit box is updated to current position
            hitBox.setY((double) y);  hitBox.setWidth((double) width);  hitBox.setX((double) x);


            //Calculates the x amount of change
            if ((!NPCMovement[1] && deltaX < 0) || (!NPCMovement[2] && deltaX > 0) || (NPCMovement[1] && NPCMovement[2])) {
                deltaX = 0;
            }
            if (Math.abs(deltaX) <= speed) {
                xMovement = deltaX;
            }
            else {
                //Left by speed if negative, right by speed if positive
                xMovement = (deltaX/Math.abs(deltaX))*speed;
            }
            //Prepares player for x checks
            hitBox.setX(x+xMovement);  hitBox.setHeight(height-2);  hitBox.setY(y+1);
            hitObstacle = false;
            //ALL X CHECKS
            for (int platformIndex = 0; platformIndex < platforms.size(); platformIndex++) {
                platformHitBox = platforms.get(platformIndex).hitBox;
                //What to do if can't move x
                if (main.CollisionChecker.checkCollision(hitBox, platformHitBox)) {
                    deltaX = 0;
                    hitObstacle = true;
                    //If hitting right
                    if (xMovement > 0) {
                        x = (int) (platformHitBox.getX()-width);
                    }
                    //If hitting left
                    else if (xMovement != 0) {
                        x = (int) (platformHitBox.getX()+platformHitBox.getWidth());
                    }
                }
            }
            //Updates positions after checks
            if (!hitObstacle) {
                x += xMovement;
            }
        }

        //Hit box is updated to current position
        hitBox.setX((double) x);  hitBox.setHeight((double) height);  hitBox.setY((double) y);

        //Angle from NPC to player
        if (gp.player.x+gp.player.width/2 < x+width/2) {
            playerAngle = 180 + (int) Math.toDegrees(Math.atan((-gp.player.y-gp.player.height/2+y+height/2)/(gp.player.x+gp.player.width/2-x-width/2+0.00001)));
        }
        else {
            playerAngle = (int) Math.toDegrees(Math.atan((-gp.player.y-gp.player.height/2+y+height/2)/(gp.player.x+gp.player.width/2-x-width/2+0.00001)));
            if (playerAngle < 0)
                playerAngle += 360;
        }

        //Rotation must be within rotationRange, inclusive
        if (playerAngle < rotationRange[0])
            rotation = rotationRange[0];
        else if (playerAngle > rotationRange[1])
            rotation = rotationRange[1];
        else
            rotation = playerAngle;

        //Dies if below map
        if (y >= gp.level.levelHeight) {
            health = 0;
        }

        //Updates variables
        if (--dmgCoolDown < 0)
            dmgCoolDown = 0;
        if (--attackCoolDown < 0)
            attackCoolDown = 0;
        if (--fireDuration < 0)
            fireDuration = 0;

        //Attacking
        if (attackCoolDown <= 0) {
            switch (behaviorID) {
                case "agressiveMelee":
                    melee();
                    break;
                case "agressiveRanged":
                    if (rotation == playerAngle)
                        shoot();
                    break;
            }
        }

        //Updates hitbox for collisions
        hitBox.setX(x);
        hitBox.setY(y);
    }
    
    //Draws NPC sprite(s) if standard (moves around in four directions)
    void drawStandard(Graphics2D g2, double GS) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = gp.level.images[spritesIDs[0]];
                break;
            case "down":
                image = gp.level.images[spritesIDs[1]];
                break;
            case "left":
                image = gp.level.images[spritesIDs[2]];
                break;
            case "right":
                image = gp.level.images[spritesIDs[3]];
                break;
        }
        
        g2.drawImage(image, (int) ((x-gp.player.x+gp.player.onScreenX)*GS), (int) ((y-gp.player.y+gp.player.onScreenY)*GS), (int) (width*GS), (int) (height*GS), null);
        if (showWeapon) {
            if (weapon.isRanged || (attackCoolDown < weapon.attackCoolDown-10)) {
                if (playerAngle > 90 && playerAngle < 270) {
                    main.ImageUtils.drawRotatedImage(g2, weapon.imageLeft, -playerAngle-180, gp.player.onScreenX-gp.player.x+x-weapon.width-weapon.x, gp.player.onScreenY-gp.player.y+y+height/2-weapon.y, weapon.width, weapon.height, gp.player.onScreenX-gp.player.x+x+width/2, gp.player.onScreenY-gp.player.y+y+height/2, GS);
                }
                else {
                    main.ImageUtils.drawRotatedImage(g2, weapon.imageRight, -playerAngle, gp.player.onScreenX-gp.player.x+x+weapon.width+weapon.x, gp.player.onScreenY-gp.player.y+y+height/2-weapon.y, weapon.width, weapon.height, gp.player.onScreenX-gp.player.x+x+width/2, gp.player.onScreenY-gp.player.y+y+height/2, GS);
                }
            }
            else {
                if (playerAngle > 90 && playerAngle < 270) {
                    main.ImageUtils.drawRotatedImage(g2, weapon.imageLeft, -playerAngle-180, gp.player.onScreenX-gp.player.x+x-weapon.width-weapon.x-15, gp.player.onScreenY-gp.player.y+y+height/2-weapon.y, weapon.width, weapon.height, gp.player.onScreenX-gp.player.x+x+width/2, gp.player.onScreenY-gp.player.y+y+height/2, GS);
                }
                else {
                    main.ImageUtils.drawRotatedImage(g2, weapon.imageRight, -playerAngle, gp.player.onScreenX-gp.player.x+x+weapon.width+weapon.x+15, gp.player.onScreenY-gp.player.y+y+height/2-weapon.y, weapon.width, weapon.height, gp.player.onScreenX-gp.player.x+x+width/2, gp.player.onScreenY-gp.player.y+y+height/2, GS);
                }
            }
        }
    }
    
    //Draws sprites if a turret (can't move but can rotate)
    void drawTurret(Graphics2D g2, double GS) {
        BufferedImage baseImage = gp.level.images[spritesIDs[0]];
        BufferedImage protrustionImage = gp.level.images[spritesIDs[1]];

        main.ImageUtils.drawRotatedImage(g2, protrustionImage, 180-rotation, x-gp.player.x+gp.player.onScreenX, y-gp.player.y+gp.player.onScreenY, width, height, x+width/2-gp.player.x+gp.player.onScreenX, y+height/2-gp.player.y+gp.player.onScreenY, GS);
        g2.drawImage(baseImage, (int) ((x-gp.player.x+gp.player.onScreenX)*GS), (int) ((y-gp.player.y+gp.player.onScreenY)*GS), (int) (width*GS), (int) (height*GS), null);
    }
    
    //Calls the proper, specific draw method
    public void draw(Graphics2D g2, double GS) {
        switch (appearanceStyle) {
            case "standard":
                drawStandard(g2, GS);
                break;
            case "turret":
                drawTurret(g2, GS);
                break;
        }
    }
}
