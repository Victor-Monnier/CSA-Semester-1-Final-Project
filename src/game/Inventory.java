package game;

import main.GamePanel;

import java.util.ArrayList;

public class Inventory {
    public int slotEquipped = 1, numWeapons, money;
    public ArrayList<Weapon> weapons = new ArrayList<Weapon>();
    public Weapon[] slots = new Weapon[10];
    GamePanel gp;

    public Inventory(GamePanel gp) {
        this.gp = gp;

        for (int i = 0; i < weapons.size(); i++)
            slots[i] = weapons.get(i);
    }
    
    //Equips item based on the slot given
    public Weapon equipItem(int slot) {
        if (slots[slot-1] != null) {
            slotEquipped = slot;
            return slots[slotEquipped - 1];
        }
        return gp.player.weapon;
    }
    
    //Returns true if the player has the item/weapon given based the item ID
    public boolean hasItem(String item) {
        for (Weapon weapon : weapons) {
            if (weapon.ID.equals(item))
                return true;
        }
        return false;
    }

    //Adds a certain amount of an item
    public void addItem(String item) {
        //Index of the colon which marks the amount of items to be given
        int colonIdex = item.indexOf(':');
        //Name of the item given
        String itemName = item.substring(0, colonIdex);
        //Number of the item given
        int numItem = Integer.parseInt(item.substring(colonIdex+1));

        //Adds the item with the corresponding name and number
        switch (itemName) {
            //Special, usable items
            case "stick":
                for (int i = 0; i < numItem; i++) {
                    weapons.add(new Weapon(gp, "stick"));
                    slots[numWeapons++] = weapons.get(weapons.size()-1);
                    if (numWeapons == 1)
                        gp.player.weapon = slots[0];
                }
                break;
            case "magic_stick":
                for (int i = 0; i < numItem; i++) {
                    weapons.add(new Weapon(gp, "magic_stick"));
                    slots[numWeapons++] = weapons.get(weapons.size()-1);
                    if (numWeapons == 1)
                        gp.player.weapon = slots[0];
                }
                break;
            case "fire_stick":
                for (int i = 0; i < numItem; i++) {
                    weapons.add(new Weapon(gp, "fire_stick"));
                    slots[numWeapons++] = weapons.get(weapons.size()-1);
                    if (numWeapons == 1)
                        gp.player.weapon = slots[0];
                }
                break;
            case "power_stick":
                for (int i = 0; i < numItem; i++) {
                    weapons.add(new Weapon(gp, "power_stick"));
                    slots[numWeapons++] = weapons.get(weapons.size()-1);
                    if (numWeapons == 1)
                        gp.player.weapon = slots[0];
                }
                break;
            case "life-steal_stick":
                for (int i = 0; i < numItem; i++) {
                    weapons.add(new Weapon(gp, "life-steal_stick"));
                    slots[numWeapons++] = weapons.get(weapons.size()-1);
                    if (numWeapons == 1)
                        gp.player.weapon = slots[0];
                }
                break;

            //Items with just a quantity
            case "money":
                money += numItem;
                break;
            case "health":
                gp.player.heal(numItem);
                break;
            case "max_health":
                gp.player.maxHealth += numItem;
                break;
            case "max_mana":
                gp.player.maxMana += numItem;
                break;
        }
    }
}
