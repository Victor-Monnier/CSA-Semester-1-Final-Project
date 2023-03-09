package game;

import java.awt.Color;

public class UnoCard
{   
    public enum CardColor {RED, GREEN, BLUE, YELLOW};
    private CardColor color; 
    private int number;
    private UnoCard(CardColor color, int number) {
        this.color = color;
        this.number = number;
    }
    
    public boolean matches(UnoCard other) {
        return this.color == other.color || this.number == other.number;
    }
    
    public static UnoCard createCard() {
        int colorID = (int) (Math.random()*4);
        switch (colorID) {
            case 0:
                return new UnoCard(CardColor.RED, (int) (Math.random()*10));
            case 1:
                return new UnoCard(CardColor.GREEN, (int) (Math.random()*10));
            case 2:
                return new UnoCard(CardColor.BLUE, (int) (Math.random()*10));
            case 3:
                return new UnoCard(CardColor.YELLOW, (int) (Math.random()*10));
        }
        System.out.println("WHY DID THIS HAPPEN?");
        return new UnoCard(CardColor.RED, 0);
    }
    
    public Color getCardColorAsColor() {
        switch (color) {
            case RED:
                return Color.red;
            case GREEN:
                return new Color(100, 185, 25);
            case BLUE:
                return Color.blue;
            case YELLOW:
                return Color.yellow;
        }
        return Color.red;
    }
    
    public CardColor getCardColorAsCardColor() {
        return color;
    }
    
    public int getNumber() {
        return number;
    }
}
