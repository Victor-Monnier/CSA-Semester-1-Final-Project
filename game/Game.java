package game;

import main.*;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

public class Game
{
    // instance variables - replace the example below with your own
    MouseHandler mouseH;
    public enum color {RED, GREEN, BLUE, YELLOW};
    private ArrayList<UnoCard> computerHand = new ArrayList<UnoCard>();
    private ArrayList<UnoCard> playerHand = new ArrayList<UnoCard>();
    private int playCardCoolDown = 0;
    private UnoCard topCard = UnoCard.createCard();
    private boolean isPlayerTurn;

    public Game(MouseHandler mouseHandler) {
        this.mouseH = mouseHandler;
        for (int i = 0; i < 7; i++) {
            computerHand.add(UnoCard.createCard());
            playerHand.add(UnoCard.createCard());
        }
        
    }
    
    public void addCard(int ID) {
        assert ID == 0 || ID == 1 : "list variable is null or empty";
        if (ID == 0)
            computerHand.add(UnoCard.createCard());
        else
            playerHand.add(UnoCard.createCard());
    }

    public void update() {
         playCardCoolDown--;
         
         boolean playerHasMatch = false;
         for (int i = 0; i < playerHand.size(); i++) {
             if (playerHand.get(i).matches(topCard))
                 playerHasMatch = true;
                }
         if (!playerHasMatch && isPlayerTurn) {
             System.out.println("SUS");
             playerHand.add(UnoCard.createCard());
         }
             
             
             
             
         if (mouseH.mouseLeftPressed) {
            if (playCardCoolDown <= 0) {
                 for (int i = 0; i < playerHand.size(); i++) {
                    if (playerHand.get(i).matches(topCard) && mouseH.mouseX >= 25+125*i && mouseH.mouseX <= 125+125*i && mouseH.mouseY >= 725 && mouseH.mouseY <= 875) {
                        playerHand.remove(i);
                        playCardCoolDown = 500;
                        return;
                    }
                }
            }
         }
    }
    
    public void draw(Graphics2D g2) {
        //Background
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, 1600, 900);
        
        g2.setColor(topCard.getCardColorAsColor());
        g2.fillRect(100, 375, 100, 150);
        g2.setColor(Color.white);
        g2.fillOval(105, 380, 90, 140);
        g2.setColor(Color.black);
        g2.fillOval(110, 385, 80, 130);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 80));
        g2.setColor(topCard.getCardColorAsColor());
        g2.drawString(topCard.getNumber()+"", 130, 475);
        
        for (int i = 0; i < computerHand.size(); i++) {
            
            UnoCard card = computerHand.get(i);
            g2.setColor(card.getCardColorAsColor());
            g2.fillRect(25+125*i, 25, 100, 150);
            /*g2.setColor(Color.white); //If not commented out, computer's cards are visible
            g2.fillOval(30+125*i, 30, 90, 140);
            g2.setColor(Color.black);
            g2.fillOval(35+125*i, 35, 80, 130);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 80));
            g2.setColor(card.getCardColorAsColor());
            g2.drawString(card.getNumber()+"", 55+125*i, 125);*/
        }
        
        for (int i = 0; i < playerHand.size(); i++) {
            UnoCard card = playerHand.get(i);
            g2.setColor(card.getCardColorAsColor());
            g2.fillRect(25+125*i, 725, 100, 150);
            g2.setColor(Color.white);
            g2.fillOval(30+125*i, 730, 90, 140);
            g2.setColor(Color.black);
            g2.fillOval(35+125*i, 735, 80, 130);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 80));
            g2.setColor(card.getCardColorAsColor());
            g2.drawString(card.getNumber()+"", 55+125*i, 825);
        }
            
    }
}
