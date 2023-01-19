package game;

import main.KeyHandler;
import main.GamePanel;
import main.CollisionChecker;

import javafx.scene.shape.Rectangle;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public Inventory inventory;
    public Weapon weapon;
    public int width = 80, height = 80,
    onScreenX = 760, onScreenY = 410,
    maxMana;
    public boolean visible = true, canInteract = true;
    public double trueHealth, mana, manaRecovery;

    public Player(GamePanel gp, KeyHandler keyH, int x, int y) {
        this.gp = gp;
        this.keyH = keyH;

        inventory = new Inventory(gp);
        weapon = inventory.slots[0];

        //Sets default values
        speed = 10;
        deltaX = 0;
        deltaY = 0;
        maxHealth = 100;
        health = maxHealth;
        maxMana = 100;
        mana = maxMana;
        manaRecovery = 20;
        direction = "down";

        //Gets image for player
        up1 = gp.player_up_1;
        up2 = gp.player_up_2;
        down1 = gp.player_down_1;
        down2 = gp.player_down_2;
        left1 = gp.player_left_1;
        left2 = gp.player_left_2;
        right1 = gp.player_right_1;
        right2 = gp.player_right_2;

        //Hit box for collisions
        hitBox = new Rectangle(x, y, width, height);

        this.x = x;
        this.y = y;
    }

    //Damage a set amount
    public void damage(int damageAmount) {
        if (dmgCoolDown <= 0) {
            health -= damageAmount;
            dmgCoolDown = 30;
        }
        if (health < 0)
            health = 0;
    }

    //Regenerates health up to maxHealth
    public void heal(int healAmount) {
        health += healAmount;
        if (health > maxHealth)
            health = maxHealth;
    }

    //Depletes mana a set amount
    public void depleteMana(double depletion) {
        mana -= depletion;
        if (mana < 0)
            mana = 0;
    }

    //Dies: resets health and resets level
    public void die() {
        gp.playSF(0);
        health = maxHealth;
        mana = maxMana;
        inventory.money /= 2;
        fireDuration = 0;
        gp.createLevel(gp.level.ID);
    }

    public void update () {
        if (health <= 0)
            die();
        if (fireDuration > 0) {
            fireDuration--;
            if (gp.level.time % 6 == 0)
                health--;
            if (gp.level.time % 2 == 0)
                gp.level.particles.add(new Particle(gp, (int) (Math.random()*(width/2)+x+width/4), (int) (Math.random()*(height/2)+y+height/4), 0, "fire"));
        }

        boolean isJumping = false, hasMoved = false, onGround = false, interactPressed = false;
        int xMovement = 0, yMovement = 0;
        ArrayList<Platform> platforms = gp.level.platforms;
        ArrayList<InteractableObject> interactables = gp.level.interactables;
        Rectangle platformHitBox;
        
        if (canInteract) {
            if (keyH.jumpPressed || keyH.upPressed) {
                direction = "up";
                isJumping = true;
            }
            if (keyH.leftPressed) {
                direction = "left";
                if (Math.abs(deltaX) <= speed)
                    deltaX -= 2;
                hasMoved = true;
            }
            if (keyH.rightPressed) {
                direction = "right";
                if (Math.abs(deltaX) <= speed)
                    deltaX += 2;
                hasMoved = true;
            }
            if (keyH.downPressed) {
                direction = "down";
                deltaY -= 1;
            }
            interactPressed = keyH.interactPressed;

            //Switching item
            if (keyH.equip1Pressed && inventory.numWeapons > 0)
                weapon = inventory.equipItem(1);
            else if (keyH.equip2Pressed && inventory.numWeapons > 1)
                weapon = inventory.equipItem(2);
            else if (keyH.equip3Pressed && inventory.numWeapons > 2)
                weapon = inventory.equipItem(3);
            else if (keyH.equip4Pressed && inventory.numWeapons > 3)
                weapon = inventory.equipItem(4);
            else if (keyH.equip5Pressed && inventory.numWeapons > 4)
                weapon = inventory.equipItem(5);
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
            platformHitBox.setX(platformHitBox.getX()+1);
            platformHitBox.setWidth(platformHitBox.getWidth()-2);
            //What to do if can't move y
            if (CollisionChecker.checkCollision(hitBox, platformHitBox)) {
                hitObstacle = true;
                deltaY = -1; //Loses speed when bonking head and makes sure momentum doesn't build up while grounded
                //If hitting floor
                if (yMovement > 0) {
                    y = (int) (platformHitBox.getY()-height);
                    onGround = true;
                }
                //If hitting ceiling
                else {
                    y = (int) (platformHitBox.getY()+platformHitBox.getHeight());
                }
            }
            platformHitBox.setX(platformHitBox.getX()-1);
            platformHitBox.setWidth(platformHitBox.getWidth()+2);
        }
        //Allow jumping
        if (isJumping && onGround) {
            deltaY = 30;
        }
        //Updates positions after checks
        if (!hitObstacle) {
            y += yMovement;
        }
        //Hit box is updated to current position
        hitBox.setY((double) y);  hitBox.setWidth((double) width);  hitBox.setX((double) x);

        //Slows down player momentum over time
        if (deltaX > 0 && !hasMoved) {
            if (deltaX > 75)
                deltaX--;
            if (deltaX > 50)
                deltaX--;
            if (deltaX > 25)
                deltaX--;
            if (deltaX > 5)
                deltaX--;
            if (deltaX > 2)
                deltaX--;
            deltaX--;
        }
        else if (deltaX < 0 && !hasMoved) {
            if (deltaX < -75)
                deltaX++;
            if (deltaX < -50)
                deltaX++;
            if (deltaX < -25)
                deltaX++;
            if (deltaX < -5)
                deltaX++;
            if (deltaX < -2)
                deltaX++;
            deltaX++;
        }
        //Calculates the x amount of change
        if ((keyH.leftPressed && deltaX > 0) || (keyH.rightPressed && deltaX < 0) || (keyH.leftPressed && keyH.rightPressed)) {
            deltaX /= 2;
        }
        //Left = negative   Right = positive
        if (gp.getGameState().equals("PLAYING"))
            xMovement = (deltaX);

        //Prepares player for x checks
        hitBox.setX(x+xMovement);  hitBox.setHeight(height-2);  hitBox.setY(y+1);
        hitObstacle = false;
        //ALL X CHECKS
        for (int platformIndex = 0; platformIndex < platforms.size(); platformIndex++) {
            platformHitBox = platforms.get(platformIndex).hitBox;
            //What to do if can't move x
            if (CollisionChecker.checkCollision(hitBox, platformHitBox)) {
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
        
        //Hit box is updated to current position
        hitBox.setX((double) x);  hitBox.setHeight((double) height);  hitBox.setY((double) y);

        //Interactables
        gp.level.popUps.clear();
        for (int interactableIndex = 0; interactableIndex < interactables.size(); interactableIndex++) {
            if (CollisionChecker.checkCollision(hitBox, interactables.get(interactableIndex).hitBox)) {
                if (interactables.get(interactableIndex).popUpText != null)
                    gp.level.popUps.add(interactables.get(interactableIndex).popUpText);
                if (interactPressed) {
                    interactables.get(interactableIndex).runInteraction();
                }
            }
        }
        
        //Dies if below map
        if (y >= gp.level.levelHeight) {
            health = 0;
        }


        //Updates player stats
        if (dmgCoolDown > 0)    //Damage cooldown. Can only be damaged twice per second
            dmgCoolDown--;
        if (attackCoolDown > 0)
            attackCoolDown--;
        mana += manaRecovery/(60.0+240.0*(mana/maxMana))+manaRecovery/60.0*(mana/maxMana*2)*(mana/maxMana*2);
        if (mana > maxMana)
            mana = maxMana;

        //Walking animation
        if (hasMoved) {
            spriteCounter++;
        }
        if (spriteCounter >= 10) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw (Graphics2D g2, double GS) {
        //Main player image
        BufferedImage image = null;
        //Up
        if (gp.mouseAngle > 60 && gp.mouseAngle < 120) {
            if (spriteNum == 1){
                image = up1;
            }
            else {
                image = up2;
            }
        }
        //Down
        else if (gp.mouseAngle > 240 && gp.mouseAngle < 300) {
            if (spriteNum == 1){
                image = down1;
            }
            else {
                image = down2;
            }
        }
        //Left
        else if (gp.mouseAngle >= 120 && gp.mouseAngle <= 240) {
            if (spriteNum == 1){
                image = left1;
            }
            else {
                image = left2;
            }
        }
        //Right
        else {
            if (spriteNum == 1){
                image = right1;
            }
            else {
                image = right2;
            }
        }

        //Only draws player if visible (duh, what did you think "visible" meant?)
        if (visible) {
            g2.drawImage(image, (int) ((onScreenX)*GS), (int) ((onScreenY)*GS), (int) (width*GS), (int) (height*GS), null);
            if (inventory.numWeapons > 0) {
                //Held item
                BufferedImage heldItemImage = weapon.imageRight;
                boolean pointingLeft = false;
                if (weapon.isRanged && gp.mouseAngle > 90 && gp.mouseAngle < 270) {
                    heldItemImage = weapon.imageLeft;
                    pointingLeft = true;
                }

                if (weapon.isRanged) {
                    if (pointingLeft) {
                        main.ImageUtils.drawRotatedImage(g2, heldItemImage, -gp.mouseAngle-180, onScreenX-weapon.width-weapon.x, onScreenY+height/2-weapon.y, weapon.width, weapon.height, onScreenX+width/2, onScreenY+height/2, GS);
                    }
                    else {
                        main.ImageUtils.drawRotatedImage(g2, heldItemImage, -gp.mouseAngle, onScreenX+weapon.width+weapon.x, onScreenY+height/2-weapon.y, weapon.width, weapon.height, onScreenX+width/2, onScreenY+height/2, GS);
                    }
                }
                else {
                    if (pointingLeft) {
                        if (attackCoolDown < 7)
                            main.ImageUtils.drawRotatedImage(g2, heldItemImage, -gp.mouseAngle, onScreenX-weapon.width-weapon.x, onScreenY+height/2-weapon.y, weapon.width, weapon.height, onScreenX+width/2, onScreenY+height/2, GS);
                        else {
                            main.ImageUtils.drawRotatedImage(g2, heldItemImage, -gp.mouseAngle, onScreenX-weapon.width-weapon.x-15, onScreenY+height/2-weapon.y, weapon.width, weapon.height, onScreenX+width/2, onScreenY+height/2, GS);
                        }
                    }
                    else {
                        if (attackCoolDown < 7)
                            main.ImageUtils.drawRotatedImage(g2, heldItemImage, -gp.mouseAngle-180, onScreenX-weapon.width-weapon.x, onScreenY+height/2-weapon.y, weapon.width, weapon.height, onScreenX+width/2, onScreenY+height/2, GS);
                        else {
                            main.ImageUtils.drawRotatedImage(g2, heldItemImage, -gp.mouseAngle-180, onScreenX-weapon.width-weapon.x-15, onScreenY+height/2-weapon.y, weapon.width, weapon.height, onScreenX+width/2, onScreenY+height/2, GS);
                        }
                    }
                }
            }
        }
    }
}
