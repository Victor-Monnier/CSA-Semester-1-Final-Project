package game;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class Weapon {
    public String ID;
    boolean isRanged;
    public int x, y, width, height,
    sprayFactor, attackCoolDown;
    public double manaUsed;
    BufferedImage imageLeft, imageRight;
    GamePanel gp;
    
    //Creates new weapon based on ID alone
    public Weapon(GamePanel gp, String ID) {
        this.gp = gp;
        this.ID = ID;

        switch(this.ID) {
            case "stick":
                isRanged = false;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                attackCoolDown = 12;
                imageLeft = gp.stick_left;
                imageRight = gp.stick_right;
                break;
            case "enemy_stick_1":
                isRanged = false;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                attackCoolDown = 60;
                imageLeft = gp.stick_left;
                imageRight = gp.stick_right;
                break;
            case "enemy_stick_2":
                isRanged = false;
                x = -15;
                y = 100;
                width = 200;
                height = 200;
                attackCoolDown = 60;
                imageLeft = gp.stick_left;
                imageRight = gp.stick_right;
                break;
            case "magic_stick":
                isRanged = true;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                sprayFactor = 10;
                attackCoolDown = 6;
                manaUsed = 10;
                imageLeft = gp.magic_stick_left;
                imageRight = gp.magic_stick_right;
                break;
            case "enemy_magic_stick":
                isRanged = true;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                sprayFactor = 10;
                attackCoolDown = 90;
                imageLeft = gp.magic_stick_left;
                imageRight = gp.magic_stick_right;
                break;
            case "fire_stick":
                isRanged = true;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                sprayFactor = 10;
                attackCoolDown = 2;
                manaUsed = 3.0;
                imageLeft = gp.fire_stick_left;
                imageRight = gp.fire_stick_right;
                break;
            case "enemy_fire_stick":
                isRanged = true;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                sprayFactor = 10;
                attackCoolDown = 30;
                imageLeft = gp.fire_stick_left;
                imageRight = gp.fire_stick_right;
                break;
            case "power_stick":
                isRanged = true;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                sprayFactor = 10;
                attackCoolDown = 6;
                manaUsed = 60;
                imageLeft = gp.power_stick_left;
                imageRight = gp.power_stick_right;
                break;
            case "life-steal_stick":
                isRanged = true;
                x = -15;
                y = 40;
                width = 80;
                height = 80;
                sprayFactor = 30;
                attackCoolDown = 2;
                manaUsed = 5.0;
                imageLeft = gp.lifeSteal_stick_left;
                imageRight = gp.lifeSteal_stick_right;
                break;
        }
    }
    
    //Gets projectiles for shooting of owned by player
    public Projectile[] getProjectiles(Player player) {
        int[] weaponEnd = getEnd(gp.player);
        switch (ID) {
            case "magic_stick":
                Projectile[] magicStickProjectiles = {
                    new Projectile(gp, weaponEnd[0], weaponEnd[1]-10, 20, 20, gp.mouseAngle+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "magic")
                };
                return magicStickProjectiles;
            case "fire_stick":
                Projectile[] fireStickProjectiles = {
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, gp.mouseAngle-10+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "fire"),
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, gp.mouseAngle-5+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "fire"),
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, gp.mouseAngle+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "fire"),
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, gp.mouseAngle+5+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "fire"),
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, gp.mouseAngle+10+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "fire")
                };
                return fireStickProjectiles;
            case "power_stick":
                Projectile[] powerStickProjectiles = {
                    new Projectile(gp, weaponEnd[0]-20, weaponEnd[1]-20, 40, 40, gp.mouseAngle+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "power")
                };
            return powerStickProjectiles;
            case "life-steal_stick":
                Projectile[] lifeStealStickProjectiles = {
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, gp.mouseAngle+(int) (Math.random()*sprayFactor-sprayFactor/2), 15, true, "life-steal")
                };
                return lifeStealStickProjectiles;
    }
    return null;
    }
    
    //Gets projectiles for shooting of owned by an npc/enemy
    public Projectile[] getProjectiles(NPC npc) {
        int[] weaponEnd = getEnd(npc);
        switch (ID) {
            case "enemy_magic_stick":
                Projectile[] projectiles = {
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, (int) (npc.rotation+Math.random()*sprayFactor-sprayFactor/2), 15, false, "magic")
                };
                return projectiles;
            case "enemy_fire_stick":
                Projectile[] fire_stick_projectiles = {
                    new Projectile(gp, weaponEnd[0]-10, weaponEnd[1]-10, 20, 20, (int) (npc.rotation+Math.random()*sprayFactor-sprayFactor/2), 10, false, "fire")
                };
                return fire_stick_projectiles;
        }
        return null;
    }
    
    //Gets the end of the weapon if owned by player
    public int[] getEnd(Player player) {
        int endX = (int) (player.x+player.width/2+(x+width)*Math.cos(Math.toRadians(gp.mouseAngle)));
        int endY = (int) (player.y+player.height/2-(y+height/2)*Math.sin(Math.toRadians(gp.mouseAngle)));
        int[] end = {endX, endY};
        return end;
    }
    
    //Gets the end of the weapon if owned by an npc/enemy
    public int[] getEnd(NPC npc) {
        int endX = (int) (npc.x+npc.width/2+(x+width)*Math.cos(Math.toRadians(npc.rotation)));
        int endY = (int) (npc.y+npc.height/2-(y+height/2)*Math.sin(Math.toRadians(npc.rotation)));
        int[] end = {endX, endY};
        return end;
    }
}
