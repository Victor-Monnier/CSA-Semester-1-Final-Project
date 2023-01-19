package gui;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

public class MenuGUI {
    GamePanel gp;
    public int ID = 0, buttonAmount;
    public boolean mouseClicked, hoveringOverButton;
    private int mouseX, mouseY, 
    upTimeHeld = 0, downTimeHeld = 0, leftTimeHeld = 0, rightTimeHeld = 0, 
    selectedButton = 1, previousMenuID = 0;
    public String bindingKey;

    //Objects shown
    MenuBG bg;
    ArrayList<MenuButton> generalButtons = new ArrayList<MenuButton>();
    ArrayList<String> dialogue = new ArrayList<String>();

    public MenuGUI(GamePanel gp) {
        this.gp = gp;
        this.ID = gp.menuID;

        //Strange things can happen when two gamestates are true during the same frame
        gp.hasChangedMenu = true;

        //Quitting
        if (this.ID == -1) {
            System.exit(0);
        }
        //Full screen menus
        if (this.ID >= 0 && this.ID < 100) {
            //The game state is always INMENU for full screen menus
            gp.setGameState("INMENU");
            //Main menu
            if (this.ID == 0) {
                previousMenuID = -1;
                generalButtons.add(0, new MenuButton(gp, 700, 350, 200, 50, 1, 1, "loadLevel:1", "PLAY", 50, 38, 40, Color.WHITE, Color.BLACK));
                generalButtons.add(0, new MenuButton(gp, 700, 425, 200, 50, 1, 2, "toMenu:20", "SETTINGS", 6, 38, 40, Color.WHITE, Color.BLACK));
                generalButtons.add(0, new MenuButton(gp, 700, 500, 200, 50, 1, 3, "toMenu:"+previousMenuID, "QUIT", 50, 38, 40, Color.WHITE, Color.BLACK));
                bg = new MenuBG(gp, 0, 0, 1600, 900, 0);
            }
            //Settings main page, fullscreen
            else if (this.ID == 20) {
                previousMenuID = 0;
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                dialogue.add("Edit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\tEdit the settings.txt file.\t");
                bg = new MenuBG(gp, 0, 0, 1600, 900, -1);
            }
        }
        //Popups (more related to the application instead of game, like the option to quit)
        else if (this.ID >= 100 && this.ID < 200) {
            //Pop up ID
            int PUID = this.ID-100;
            //Closes pop-up
            if (PUID == 0) {
                gp.setGameState("PLAYING");
                bg = null;
            }
            //Pause menu
            if (PUID == 1) {
                gp.setGameState("PAUSED");
                previousMenuID = 100;
                generalButtons.add(0, new MenuButton(gp, 675, 390, 250, 50, 1, 1, "toMenu:100", "Resume", 50, 45, 40, Color.WHITE, Color.BLACK));
                generalButtons.add(0, new MenuButton(gp, 675, 465, 250, 50, 1, 2, "toMenu:102", "Quit", 50, 45, 40, Color.WHITE, Color.BLACK));
                bg = new MenuBG(gp, 650, 365, 300, 175, 1);
            }
            //Confirming quitting from pause menu
            else if (PUID == 2) {
                gp.setGameState("PAUSED");
                previousMenuID = 101;
                dialogue.add(0, "Really giving up?");
                generalButtons.add(0, new MenuButton(gp, 625, 500, 150, 50, 1, 2, "toMenu:0", "Yes", 50, 45, 40, Color.WHITE, Color.BLACK));
                generalButtons.add(0, new MenuButton(gp, 825, 500, 150, 50, 1, 1, "toMenu:101", "No", 50, 45, 40, Color.WHITE, Color.BLACK));
                bg = new MenuBG(gp, 600, 325, 400, 250, 1);
            }
        }
        //In-game pop-up menus
        else if (this.ID >= 200 && this.ID < 1000) {
            //Pop up ID
            int PUID = this.ID-200;
            //Closes pop-up
            if (PUID == 0) {
                gp.setGameState("PLAYING");
                gp.player.canInteract = true;
            }
            //Tutorial dialogue
            else if (PUID == 101) {
                gp.setGameState("INGAMEMENU");
                previousMenuID = 200;
                dialogue.add(0, "Tutorial:");
                dialogue.add(1, "wasd-direction | space-jump | r-interact");
                dialogue.add(2, "escape-pause/close menu (including this one)");
                bg = new MenuBG(gp, 400, 200, 800, 300, 3);
            }
            //Sign with info on health and mana
            else if (PUID == 102) {
                gp.setGameState("INGAMEMENU");
                previousMenuID = 200;
                dialogue.add(0, "You can aim with your mouse.");
                dialogue.add(1, "Left-click attacks with the selected weapon.");
                dialogue.add(2, "You don't have any right now, but you");
                dialogue.add(3, "can switch weapons with the number keys.");
                dialogue.add(4, "The red bar is health, which you need to keep above 0.");
                dialogue.add(5, "The blue bar is mana, which is used for spells.");
                dialogue.add(6, "The more mana you have, the faster it regenerates, so");
                dialogue.add(7, "it is important to manage your mana expenditure.");
                dialogue.add(8, "Also, try not to fall!");
                bg = new MenuBG(gp, 300, 178, 1000, 544, 3);
            }
            //Troll message below level 1
            else if (PUID == 103) {
                gp.setGameState("INGAMEMENU");
                previousMenuID = 200;
                dialogue.add(0, "You are stuck!");
                dialogue.add(1, "LOL haha");
                dialogue.add(2, "L + bozo + ratio");
                dialogue.add(3, "");
                dialogue.add(4, "-Victor");
                bg = new MenuBG(gp, 400, 200, 800, 300, 3);
            }
            //Shop instructions
            else if (PUID == 104) {
                gp.setGameState("INGAMEMENU");
                previousMenuID = 200;
                dialogue.add(0, "This is a shop!");
                dialogue.add(1, "You can spend your coins on health refill, ");
                dialogue.add(2, "increasing max health, increasing max stamina,");
                dialogue.add(3, "and buying new weapons!");
                bg = new MenuBG(gp, 400, 200, 800, 300, 3);
            }
            //Gauntlet explantation
            else if (PUID == 105) {
                gp.setGameState("INGAMEMENU");
                previousMenuID = 200;
                dialogue.add(0, "Below lies an infinite trial, where enemies will");
                dialogue.add(1, "spawn until you die.");
                dialogue.add(2, "");
                dialogue.add(3, "How long can you last?");
                bg = new MenuBG(gp, 400, 200, 800, 300, 3);
            }
            //Score report from gauntlet
            else if (PUID == 106) {
                gp.setGameState("INGAMEMENU");
                previousMenuID = 200;
                dialogue.add(0, "You lasted "+gp.timeInTrial/60+" seconds!");
                gp.timeInTrial = 0;
                bg = new MenuBG(gp, 400, 410, 800, 80, 3);
            }
        }
        //Nothing happens if wrong menu and prints the error 10 times
        else {
            for (int printErrorIndex = 0; printErrorIndex < 10; printErrorIndex++) {
                System.out.println("ERROR: menu with ID "+this.ID+" does not exist!");
            }
            System.exit(0);
        }
    
        //Variables involving buttons generally
        buttonAmount = generalButtons.size();
    }

    public int update() {
        //Binds the new key
        if (this.ID == 23) {
            if (bindingKey != null && gp.keyH.lastKeyPressed != null) {
                switch (bindingKey) {
                    case "jumpCode":
                        gp.jumpCode = gp.keyH.lastKeyPressed;
                        break;
                    case "downCode":
                        gp.downCode = gp.keyH.lastKeyPressed;
                        break;
                    case "leftCode":
                        gp.leftCode = gp.keyH.lastKeyPressed;
                        break;
                    case "rightCode":
                        gp.rightCode = gp.keyH.lastKeyPressed;
                        break;
                    case "interactCode":
                        gp.interactCode = gp.keyH.lastKeyPressed;
                        break;
                }
                bindingKey = null;
                gp.updateKeyBindings();
            }
        }

        //Mouse input and variables
        boolean hasMoved = false;
        if (mouseX != gp.mouseX || mouseY != gp.mouseY) {
            hasMoved = true;
        }
        mouseX = gp.mouseX;
        mouseY = gp.mouseY;
        hoveringOverButton = false;
        mouseClicked = false;
        if (gp.mouseLeftClicked) {
            mouseClicked = true;
        }

        //Keyboard input and variables
        if (gp.keyH.downArrowPressed) downTimeHeld++;
        else {downTimeHeld = 0;}
        if (gp.keyH.upArrowPressed) upTimeHeld++;
        else upTimeHeld = 0;
        if (gp.keyH.leftArrowPressed) leftTimeHeld++;
        else {leftTimeHeld = 0;}
        if (gp.keyH.rightArrowPressed) rightTimeHeld++;
        else rightTimeHeld = 0;

        //Previous menu
        if (gp.keyH.escapePressed) {
            gp.createMenu(previousMenuID);
            return(0);
        }

        //Button that is selected found using arrows
        if ((downTimeHeld % 6 == 1 && downTimeHeld > 18 || downTimeHeld == 1) || (rightTimeHeld % 6 == 1 && rightTimeHeld > 18 || rightTimeHeld == 1)) {
            if (selectedButton+1 <= buttonAmount) selectedButton++;
            else selectedButton = 1;
        }
        if ((upTimeHeld % 6 == 1 && upTimeHeld > 18 || upTimeHeld == 1) || (leftTimeHeld % 6 == 1 && leftTimeHeld > 18 || leftTimeHeld == 1)) {
            if (selectedButton-1 > 0) selectedButton--;
            else selectedButton = buttonAmount;
        }
        
        //Buttons that send to other menus or a level
        for (int buttonIndex = 0; buttonIndex < generalButtons.size(); buttonIndex++) {
            MenuButton thisButton = generalButtons.get(buttonIndex);
            //If hovering over it OR selected by key board
            if (hasMoved && (thisButton.x <= mouseX && thisButton.x+thisButton.width >= mouseX) && (thisButton.y <= mouseY && thisButton.y+thisButton.height >= mouseY) || thisButton.ID == selectedButton) {
                hoveringOverButton = true;
                selectedButton = thisButton.ID;
                thisButton.isSelected = true;
                if ((mouseClicked && ((thisButton.x <= mouseX && thisButton.x+thisButton.width >= mouseX) && (thisButton.y <= mouseY && thisButton.y+thisButton.height >= mouseY))) || gp.keyH.enterPressed) {
                    thisButton.runInteraction();
                    return(0);
                }
            }
            else {
                thisButton.isSelected = false;
            }
        }

        return(0);
    }

    public void draw(Graphics2D g2, double GS) {
        //Background
        if (bg != null) 
            bg.draw(g2, GS);

        //Buttons that send to other menus or a level
        for (int buttonIndex = 0; buttonIndex < generalButtons.size(); buttonIndex++) {
            generalButtons.get(buttonIndex).draw(g2, GS);                          
        }
        
        //Dialogue
        g2.setFont(new Font("TimesRoman", Font.PLAIN, (int) (40*GS)));
        g2.setColor(Color.WHITE);
        for (int dialogueIndex = 0; dialogueIndex < dialogue.size(); dialogueIndex++) {
            g2.drawString(dialogue.get(dialogueIndex), (int) ((bg.x+10)*GS), (int) ((bg.y+50+60*dialogueIndex)*GS));
        }
    }
}
