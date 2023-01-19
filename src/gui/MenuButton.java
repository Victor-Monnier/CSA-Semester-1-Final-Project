package gui;

import main.GamePanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MenuButton {
    public int x, y, width, height, spriteID, ID, labelX, labelY, labelSize;
    public boolean isSelected = false;
    public String effectID, labelText;
    public Color unselectedFontColor, selectedFontColor;
    public BufferedImage unselectedImage, selectedImage;
    GamePanel gp;

    public MenuButton(GamePanel gp, int x, int y, int width, int height, int spriteID, int ID, String effectID, 
    String labelText, int labelX, int labelY, int labelSize, Color unselectedFontColor, Color selectedFontColor) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.spriteID = spriteID;
        this.ID = ID;
        this.effectID = effectID;
        this.labelX = labelX;
        this.labelY = labelY;
        this.labelSize = labelSize;
        this.labelText = labelText;
        this.unselectedFontColor = unselectedFontColor;
        this.selectedFontColor = selectedFontColor;
        
        getButtonImage();
    }

    public void runInteraction() {
        if (effectID.startsWith("toMenu:")) {
            gp.createMenu(Integer.parseInt(effectID.substring(7)));
        }
        else if (effectID.startsWith("loadLevel:")) {
            gp.createLevel(Integer.parseInt(effectID.substring(10)));
        }
    }

    public void getButtonImage() {
        switch (spriteID) {
            case 1:
                unselectedImage = gp.gui_button_1;
                selectedImage = gp.gui_button_1_selected;
                break;
            case 2:
                unselectedImage = gp.gui_item_slot;
                selectedImage = gp.gui_item_slot_selected;
                break;
            default:
                unselectedImage = gp.gui_button_1;
                selectedImage = gp.gui_button_1_selected;
                break;
        }
    }

    //Updates its display
    public void draw(Graphics2D g2, double GS) {
        g2.setFont(new Font("TimesRoman", Font.PLAIN, (int) (labelSize*GS)));
        if (isSelected) {
            g2.drawImage(selectedImage, (int) (x*GS), (int) (y*GS), (int) (width*GS), (int) (height*GS), null);
            g2.setColor(selectedFontColor);
        }
        else {
            g2.drawImage(unselectedImage, (int) (x*GS), (int) (y*GS), (int) (width*GS), (int) (height*GS), null);
            g2.setColor(unselectedFontColor);
        }
        g2.drawString(labelText, (int) ((x+labelX)*GS), (int) ((y+labelY)*GS));
    }
}
