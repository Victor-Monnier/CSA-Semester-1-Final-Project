package game;

import main.ImageUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

public class HUD {
    //Draws sprite
    public static void draw (Graphics2D g2, main.GamePanel gp, double GS) {
        //Health
        g2.drawImage(gp.heart, 20, 20, 40, 40, null);
        g2.setColor(Color.BLACK);
        g2.fillRect((int) (80*GS), (int) (20*GS), (int) ((10+5*gp.player.maxHealth)*GS), (int) (40*GS));
        g2.setColor(Color.RED);
        g2.fillRect((int) (85*GS), (int) (25*GS), (int) ((5*gp.player.health)*GS), (int) (30*GS));

        //Player mana
        g2.drawImage(gp.crystal, (int) (20*GS), (int) (80*GS), (int) (40*GS), (int) (40*GS), null);
        g2.setColor(Color.BLACK);
        g2.fillRect((int) (80*GS), (int) (80*GS), (int) ((10+5*gp.player.maxMana)*GS), (int) (40*GS));
        g2.setColor(new Color(0, 139, 139));
        g2.fillRect((int) (85*GS), (int) (85*GS), (int) ((5*gp.player.mana)*GS), (int) (30*GS));

        //Money
        g2.setFont(new Font("TimesRoman", Font.PLAIN, (int) (40*GS)));
        g2.setColor(new Color(255, 210, 0));
        g2.drawImage(gp.coin, 20, 140, 40, 40, null);
        g2.drawString(""+gp.player.inventory.money, 80, 172);
        g2.setColor(Color.WHITE);

        //Hot-bar. Item is titlted. Box around item is enlarged if equipped
        for (int weaponIndex = 0; weaponIndex < gp.player.inventory.numWeapons; weaponIndex++) {
            if (gp.player.inventory.slotEquipped == weaponIndex+1)
                g2.drawImage(gp.gui_item_slot_selected, (int) (1484*GS), (int) ((4+120*weaponIndex)*GS), (int) (112*GS), (int) (112*GS), null);
            else
                g2.drawImage(gp.gui_item_slot, (int) (1490*GS), (int) ((10+120*weaponIndex)*GS), (int) (100*GS), (int) (100*GS), null);
            ImageUtils.drawRotatedImage(g2, gp.player.inventory.slots[weaponIndex].imageRight, 315, 1505, 25+120*weaponIndex, 70, 70, 1540, 60+120*weaponIndex, GS);
        }
    
        //Pop-ups, like "press r to interact"
        for (int popUpIndex = 0; popUpIndex < gp.level.popUps.size(); popUpIndex++) {
            g2.drawImage(gp.gui_item_slot, (int) (600*GS), (int) ((550+80*popUpIndex)*GS), (int) (400*GS), (int) (60*GS), null);
            g2.drawString(gp.level.popUps.get(popUpIndex), (int) ((620)*GS), (int) ((595+80*popUpIndex)*GS));
        }
    }
}
 