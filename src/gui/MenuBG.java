package gui;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuBG {
    GamePanel gp;
    int x, y, width, height, spriteID;
    public BufferedImage image;

    public MenuBG(GamePanel gp, int x, int y, int width, int height, int spriteID) {
        this.gp = gp;
        this.spriteID = spriteID;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteID = spriteID;
        getBGImage();
    }
    
    //Gets images based on spriteID
    public void getBGImage() {
        switch (spriteID) {
            //Game backgrounds
            case 0:
                image = gp.main_menu_background;
                break;
            case 1:
                image = gp.menu_background_1;
                break;
            default:
                image = gp.menu_background_1;;
        }
    }
    
    //Draws sprite
    public void draw (Graphics2D g2, double GS) {
        g2.drawImage(image, (int) (x*GS), (int) (y*GS), (int) (width*GS), (int) (height*GS), null);
    }
}

