package game;

import main.KeyHandler;
import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameBG {
    GamePanel gp;
    Level level;
    KeyHandler keyH;
    private int width, height;
    BufferedImage background, layer1, layer2, layer3, foreground;
    //BufferedImage[] layers;

    public GameBG(GamePanel gp, Level level, int levelWidth, int levelHeight, int[] spriteIDs) {
        this.gp = gp;
        this.level = level;
        this.keyH = gp.keyH;
        width = levelWidth;
        height = levelHeight;

        background = level.images[spriteIDs[0]];
        layer1 = level.images[spriteIDs[1]];
        layer2 = level.images[spriteIDs[2]];
        layer3 = level.images[spriteIDs[3]];
        foreground = level.images[spriteIDs[4]];
    }
    
    //Everything behind the player
    public void drawBackground(Graphics2D g2, double GS) {
        //Background: static; doesn't move with player      Dimensions: 1600 x 900
        g2.drawImage(background, 0, 0, (int) (1600*GS), (int) (900*GS), null);
        //Layer 1: moves 1/10 as fast as player             Dimensions: level width/10 x level height/10
        g2.drawImage(layer1, (int) ((-gp.player.x/10)*GS), (int) ((-gp.player.y/10)*GS), (int) ((width/10+gp.player.onScreenX*2+gp.player.width)*GS), (int) ((height/10+gp.player.onScreenY*2+gp.player.height*0.9)*GS), null);
        //Layer 2: moves 1/4 as fast                        Dimensions: ?????
        g2.drawImage(layer2, (int) ((-gp.player.x/4)*GS), (int) ((-gp.player.y/4)*GS), (int) ((width/4+gp.player.onScreenX*2+gp.player.width*0.75)*GS), (int) ((height/4+gp.player.onScreenY*2+gp.player.height*0.75)*GS), null);
        //Layer 3: moves as fast but behind player          Dimensions: level width x level height
        g2.drawImage(layer3, (int) ((-gp.player.x)*GS), (int) ((-gp.player.y)*GS), (int) ((width+gp.player.onScreenX*2)*GS), (int) ((height+gp.player.onScreenY*2)*GS), null);
    }

    //The foreground: the only thing in front of the player
    public void drawForeground(Graphics2D g2, double GS) {
        //Foreground: moves with player but in front        Dimensions: level width x level height
        g2.drawImage(foreground, (int) ((-gp.player.x)*GS), (int) ((-gp.player.y)*GS), (int) ((width+gp.player.onScreenX*2)*GS), (int) ((height+gp.player.onScreenY*2)*GS), null);
    }
}

