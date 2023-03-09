package game;

import main.GamePanel;
import main.KeyHandler;
import main.ImageUtils;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javafx.scene.shape.Rectangle;

public class InteractableObject {
    GamePanel gp;
    Level level;
    KeyHandler keyH;
    private BufferedImage image;
    public Rectangle hitBox;
    public int x, y, width, height, rotation = 0, timesUsed, spriteID;
    public String animationID, interactionID, popUpText; 
    public boolean alive = true;
    double GS;
    
    //If there is a pop-up message
    public InteractableObject(GamePanel gp, Level level, int x, int y, int width, int height, int timesUsed, int spriteID, String animationID, String interactionID, String popUpText) {
        this.gp = gp;
        this.level = level;
        this.GS = gp.GS;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.timesUsed = timesUsed;
        this.spriteID = spriteID;
        this.animationID = animationID;
        this.interactionID = interactionID;
        this.popUpText = popUpText;

        image = level.images[spriteID];

        hitBox = new Rectangle(x, y, width, height);
    }
    //If there is NOT a pop-up message
    public InteractableObject(GamePanel gp, Level level, int x, int y, int width, int height, int timesUsed, int spriteID, String animationID, String interactionID) {
        this.gp = gp;
        this.level = level;
        this.GS = gp.GS;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.timesUsed = timesUsed;
        this.spriteID = spriteID;
        this.animationID = animationID;
        this.interactionID = interactionID;

        image = level.images[spriteID];

        hitBox = new Rectangle(x, y, width, height);
    }
    
    //When interacted with by player
    public void runInteraction() {
        if (interactionID.contains("openDialogue:")) {
            gp.createMenu(300+Integer.parseInt(interactionID.substring(13)));
        }
        if (interactionID.contains("addItem:")) {
            gp.player.inventory.addItem(interactionID.substring(8));
            timesUsed--;
        }
        if (interactionID.contains("buyItem:")) {
            int firstColon = interactionID.indexOf(':');
            int secondColon = interactionID.substring(firstColon+1).indexOf(':')+firstColon+1;
            int cost = Integer.parseInt(interactionID.substring(firstColon+1, secondColon));
            if (gp.player.inventory.money >= cost) {
                gp.player.inventory.money -= cost;
                System.out.println(interactionID.substring(secondColon+1));
                gp.player.inventory.addItem(interactionID.substring(secondColon+1));
                timesUsed--;
            }

            gp.keyH.interactPressed = false;
        }
        
        if (timesUsed <= 0)
            alive = false;
    }
    
    //Logical updates
    public void update() {
        switch (animationID) {
            case "spin":
                rotation = ++rotation % 360;
                break;
            case "bob":
                if (gp.level.time % 3 == 0) {
                    if (gp.level.time % 120 < 60)
                        y--;
                    else
                        y++;
                }
                break;
            default:
                break;
        }
    }

    //Draws sprite
    public void draw (Graphics2D g2, double GS) {
        ImageUtils.drawRotatedImage(g2, image, rotation, x-gp.player.x+gp.player.onScreenX, y-gp.player.y+gp.player.onScreenY, width, height, (int) (x-gp.player.x+gp.player.onScreenX+width/2), (int) (y-gp.player.y+gp.player.onScreenY+height/2), GS);
    }
}
