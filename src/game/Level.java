package game;

import main.KeyHandler;
import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level {
    //General
    public int ID = 0,
    levelWidth, levelHeight;
    public long time = 0;
    public String name;
    private GameBG levelBG;
    GamePanel gp;
    KeyHandler keyH;

    //In-game objects
    Player player;
    public ArrayList<InteractableObject> interactables = new ArrayList<InteractableObject>();
    public ArrayList<Platform> platforms = new ArrayList<Platform>();
    public ArrayList<Particle> particles = new ArrayList<Particle>();
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
    public ArrayList<AOE> aoes = new ArrayList<AOE>();
    public ArrayList<NPC> npcs = new ArrayList<NPC>();
    public ArrayList<String> popUps = new ArrayList<String>();

    //Level specific images
    public BufferedImage[] images = new BufferedImage[25];

    //Flags used to keep track of level events
    public FlagChecker FC;
    public ArrayList<String> flags = new ArrayList<String>();

    public Level(GamePanel gp, KeyHandler keyH, int levelID) {
        this.gp = gp;
        this.keyH = keyH;
        this.ID = levelID;

        getImages();
        
        if (this.ID == 1) {
            levelWidth = 10000;
            levelHeight = 2000;

            int[] BGSprites = {0, 1, 2, 3, 4};
            this.levelBG = new GameBG(gp, this, levelWidth, levelHeight, BGSprites);

            platforms.add(new Platform(gp, this, -100, 0, 100, levelHeight, 6, 0));          //Left Border
            platforms.add(new Platform(gp, this, levelWidth, 0, 100, levelHeight, 7, 0));    //Right Border
            platforms.add(new Platform(gp, this, 0, -100, levelWidth, 100, 5, 0));          //Top Border

            platforms.add(new Platform(gp, this, 0, 1000, 1500, 800, 6, 0));       
            platforms.add(new Platform(gp, this, 0, 1950, 1500, 550, 6, 0));
            platforms.add(new Platform(gp, this, 0, 1800, 100, 150, 6, 0));        
            platforms.add(new Platform(gp, this, 1400, 950, 100, 50, 6, 0));      
            platforms.add(new Platform(gp, this, 2000, 1000, 350, 1500, 6, 0));
            platforms.add(new Platform(gp, this, 2950, 900, 200, 200, 6, 0));
            platforms.add(new Platform(gp, this, 3600, 800, 200, 1700, 6, 0));
            platforms.add(new Platform(gp, this, 3800, 800, 200, 50, 6, 1));
            platforms.add(new Platform(gp, this, 4000, 0, 500, 1500, 6, 0));
            platforms.add(new Platform(gp, this, 4500, 1100, 900, 400, 6, 0));
            platforms.add(new Platform(gp, this, 5200, 400, 200, 420, 6, 0));// alskjdg aslj
            platforms.add(new Platform(gp, this, 4500, 400, 700, 380, 6, 0)); //alksdgjf
            platforms.add(new Platform(gp, this, 5200, 940, 200, 160, 6, 0));
            platforms.add(new Platform(gp, this, 4000, 1950, 2000, 550, 6, 0));
            platforms.add(new Platform(gp, this, 4500, 780, 540, 160, 6, 0));
            platforms.add(new Platform(gp, this, 5040, 860, 80, 80, 6, 0));
            platforms.add(new Platform(gp, this, 5120, 940, 80, 80, 6, 2));
            platforms.add(new Platform(gp, this, 6000, 1200, 1500, 1300, 6, 0));
            platforms.add(new Platform(gp, this, 6000, 950, 1000, 150, 6, 0));
            platforms.add(new Platform(gp, this, 5000, 1850, 20, 100, 6, 0));
            platforms.add(new Platform(gp, this, 5750, 1763, 250, 50, 6, 0));
            platforms.add(new Platform(gp, this, 5775, 1576, 225, 50, 6, 0));
            platforms.add(new Platform(gp, this, 5800, 1387, 200, 50, 6, 0));
            platforms.add(new Platform(gp, this, 5830, 1200, 175, 50, 6, 0));
            platforms.add(new Platform(gp, this, 5850, 1050, 150, 50, 6, 0));
            platforms.add(new Platform(gp, this, 7000, 1050, 150, 50, 6, 0));
            platforms.add(new Platform(gp, this, 7500, 1950, 2500, 550, 6, 0));
            platforms.add(new Platform(gp, this, 8000, 1050, 100, 100, 6, 0));
            platforms.add(new Platform(gp, this, 8600, 900, 100, 100, 6, 0));
            platforms.add(new Platform(gp, this, 8000, 750, 100, 100, 6, 0));
            platforms.add(new Platform(gp, this, 7400, 600, 100, 100, 6, 0));
            platforms.add(new Platform(gp, this, 6800, 450, 100, 100, 6, 0));
            platforms.add(new Platform(gp, this, 5000, 0, 1120, 300, 6, 0));
            platforms.add(new Platform(gp, this, 6100, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 6000, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 5900, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 5800, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 5700, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 5600, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 5500, 400, 20, 20, 6, 0));
            platforms.add(new Platform(gp, this, 9200, 750, 800, 100, 6, 0));


            aoes.add(new AOE(gp, 9900, 0, 100, 2000, 0, 0, 5, "nextLevel"));

            interactables.add(new InteractableObject(gp, this, 600, 900, 100, 100, 1, 16, "static", "openDialogue:2"));
            interactables.add(new InteractableObject(gp, this, 100, 1850, 100, 100, 1, 16, "static", "openDialogue:3"));
            interactables.add(new InteractableObject(gp, this, 7400, 500, 100, 100, 1, 14, "static", "addItem:money:60", "Chest!"));
            interactables.add(new InteractableObject(gp, this, 6800, 350, 100, 100, 1, 14, "static", "addItem:money:75", "Chest!"));
            interactables.add(new InteractableObject(gp, this, 9300, 650, 100, 100, 1, 14, "static", "addItem:money:50", "Chest!"));
            if (!gp.player.inventory.hasItem("stick"))
                interactables.add(new InteractableObject(gp, this, 4200, 1850, 100, 100, 1, 8, "spin", "addItem:stick:1", "Stick"));
            if (!gp.player.inventory.hasItem("magic_stick"))
                interactables.add(new InteractableObject(gp, this, 4500, 1000, 100, 100, 1, 9, "spin", "addItem:magic_stick:1", "Stick of Magic"));
            if (!gp.player.inventory.hasItem("fire_stick"))
                interactables.add(new InteractableObject(gp, this, 4700, 300, 100, 100, 1, 15, "spin", "addItem:fire_stick:1", "Stick of Fire"));


            int[][] NPCSprites = {{10, 11, 12, 13}, {5, 8}, {5, 15}};
            npcs.add(new NPC(gp, 4800, 950, "meleeEnemy1", NPCSprites[0]));
            npcs.add(new NPC(gp, 8000, 1700, "meleeEnemy1", NPCSprites[0]));
            npcs.add(new NPC(gp, 8000, 1700, "meleeEnemy2", NPCSprites[0]));
            npcs.add(new NPC(gp, 5600, 1850, "turret1", NPCSprites[1]));
            npcs.add(new NPC(gp, 6000, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6100, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6200, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6300, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6400, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6500, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6600, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6700, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6800, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 6900, 850, "turret2", NPCSprites[1]));
            npcs.add(new NPC(gp, 8280, -25, "turret3", NPCSprites[2]));

            flags.add("1");
            flags.add("2");

            gp.player.x = 100;
            gp.player.y = 100;

            //Opens tutorial menu
            gp.createMenu(301);
        }
        if (this.ID == 2) {
            levelWidth = 2200;
            levelHeight = 4200;

            int[] BGSprites = {0, 1, 2, 3, 4};
            this.levelBG = new GameBG(gp, this, levelWidth, levelHeight, BGSprites);

            platforms.add(new Platform(gp, this, -100, 0, 100, levelHeight, 5, 0));          //Left Border
            platforms.add(new Platform(gp, this, levelWidth, 0, 100, levelHeight, 5, 0));    //Right Border
            platforms.add(new Platform(gp, this, 0, -100, levelWidth, 100, 5, 0));          //Top Border

            platforms.add(new Platform(gp, this, 0, 0, 100, 6000, 6, 0));
            platforms.add(new Platform(gp, this, 2100, 0, 100, 6000, 6, 0));
            platforms.add(new Platform(gp, this, 600, 0, 1600, 900, 6, 0));
            platforms.add(new Platform(gp, this, 100, 1900, 1800, 200, 6, 0));
            platforms.add(new Platform(gp, this, 800, 2500, 600, 50, 6, 0));
            platforms.add(new Platform(gp, this, 650, 2650, 900, 50, 6, 0));
            platforms.add(new Platform(gp, this, 500, 2800, 1200, 50, 6, 0));
            platforms.add(new Platform(gp, this, 350, 2950, 1500, 50, 6, 0));
            platforms.add(new Platform(gp, this, 200, 3100, 1800, 50, 6, 0));


            aoes.add(new AOE(gp, 9900, 0, 100, 2000, 0, 0, 5, "nextLevel"));


            interactables.add(new InteractableObject(gp, this, 300, 1800, 100, 100, 1, 16, "static", "openDialogue:4"));
            interactables.add(new InteractableObject(gp, this, 1800, 1800, 100, 100, 1, 16, "static", "openDialogue:5"));
            if (!gp.player.inventory.hasItem("power_stick"))
                interactables.add(new InteractableObject(gp, this, 500, 1800, 100, 100, 1, 17, "bob", "buyItem:100:power_stick:1", "100:Stick of Power"));
            if (!gp.player.inventory.hasItem("life-steal_stick"))
                interactables.add(new InteractableObject(gp, this, 700, 1800, 100, 100, 1,18, "bob", "buyItem:100:life-steal_stick:1", "100:Stick of Life-Steal"));
            interactables.add(new InteractableObject(gp, this, 900, 1800, 100, 100, 5,19, "bob", "buyItem:"+(gp.player.maxHealth/5)+":max_health:10", (gp.player.maxHealth/5)+":Increase Max Health"));
            interactables.add(new InteractableObject(gp, this, 1100, 1800, 100, 100, 5,20, "bob", "buyItem:"+(gp.player.maxMana/5)+":max_mana:10", (gp.player.maxMana/5)+":Increase Max Mana"));
            interactables.add(new InteractableObject(gp, this, 1300, 1800, 100, 100, (gp.player.health+5)/10+50,21, "bob", "buyItem:10:health:10", "10:Heal 10 Health"));

            flags.add("trial");

            gp.player.x = 310;
            gp.player.y = 0;

            //Shows previous score
            if (gp.timeInTrial != 0)
                gp.createMenu(306);
        }
    
        FC = new FlagChecker(gp, this);
    }

    public void getImages() {
        System.out.println("Loading graphics... ");
        gp.loadingGraphics = true;
        switch(ID) {
            case 1:
                try {
                    images[0] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_1.png"));
                    images[1] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_2.png"));
                    images[2] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_2.png"));
                    images[3] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_2.png"));
                    images[4] = null;
                    images[5] = null;
                    images[6] = ImageIO.read(getClass().getResourceAsStream("/res/platform/platform_1.png"));
                    images[7] = ImageIO.read(getClass().getResourceAsStream("/res/platform/platform_2.png"));
                    images[8] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/stick_left.png"));
                    images[9] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/magic_stick_left.png"));
                    images[10] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_up.png"));
                    images[11] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_down.png"));
                    images[12] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_left.png"));
                    images[13] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_right.png"));
                    images[14] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/interactable/chest.png"));
                    images[15] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/fire_stick_left.png"));
                    images[16] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/interactable/sign.png"));
                    images[17] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/power_stick_left.png"));
                    images[18] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/life-steal_stick_left.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    images[0] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_1.png"));
                    images[1] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_2.png"));
                    images[2] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_2.png"));
                    images[3] = ImageIO.read(getClass().getResourceAsStream("/res/background/game/background_2.png"));
                    images[4] = null;
                    images[5] = null;
                    images[6] = ImageIO.read(getClass().getResourceAsStream("/res/platform/platform_1.png"));
                    images[7] = ImageIO.read(getClass().getResourceAsStream("/res/platform/platform_2.png"));
                    images[8] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/stick_left.png"));
                    images[9] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/magic_stick_left.png"));
                    images[10] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_up.png"));
                    images[11] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_down.png"));
                    images[12] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_left.png"));
                    images[13] = ImageIO.read(getClass().getResourceAsStream("/res/entity/enemy/pacMan_right.png"));
                    images[14] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/interactable/chest.png"));
                    images[15] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/fire_stick_left.png"));
                    images[16] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/interactable/sign.png"));
                    images[17] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/power_stick_left.png"));
                    images[18] = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/life-steal_stick_left.png"));
                    images[19] = gp.heart_plus;
                    images[20] = gp.crystal_plus;
                    images[21] = gp.heart;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    for (int i = 0; i < 30; i++)
                        images[i] = images[18] = ImageIO.read(getClass().getResourceAsStream("/res/wip/hit_box.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        System.out.println("Done loading graphics");
        gp.loadingGraphics = false;
    }

    //Player shoots a projectile
    void shoot() {
        if (gp.player.attackCoolDown == 0 && gp.getGameState().equals("PLAYING") && gp.player.mana > gp.player.weapon.manaUsed) {
            for (Projectile projectile : gp.player.weapon.getProjectiles(player))
                projectiles.add(projectile);
            gp.player.attackCoolDown = gp.player.weapon.attackCoolDown;
            gp.player.depleteMana(gp.player.weapon.manaUsed);
        }
    }

    //Player does a melee attack with a stick
    void melee() {
        if (gp.player.attackCoolDown == 0) {
            if (gp.getGameState().equals("PLAYING")) {
                aoes.add(new AOE(gp, player, 0, 0, 30, 30, 0, 0, 5, "melee"));
                aoes.add(new AOE(gp, player, -gp.player.weapon.width/2, -gp.player.weapon.height/2, 30, 30, 0, 0, 5, "melee"));
                aoes.add(new AOE(gp, player, -gp.player.weapon.width, -gp.player.weapon.height, 30, 30, 0, 0, 5, "melee"));
                gp.player.attackCoolDown = gp.player.weapon.attackCoolDown;
            }
        }
    }

    //Adds an npc (enemy) to the level
    public void addNPC(int x, int y, String npcID, int[] spriteIDs) {
        npcs.add(new NPC(gp, x, y, npcID, spriteIDs));
    }

    public void update() {
        //Updates time in frames
        time++;

        //Plays events for the level
        levelEvents();

        //Click to shoot
        if (gp.player.inventory.numWeapons > 0) {
            if (gp.player.weapon.isRanged && gp.mouseLeftPressed) {
                shoot();
            }
            else if (gp.mouseLeftPressed) {
                melee();
            }
        }

        //Updates platforms
        for (int platformIndex = 0; platformIndex < platforms.size(); platformIndex++) {
            platforms.get(platformIndex).update();
            if (!platforms.get(platformIndex).alive) {
                platforms.remove(platformIndex);
                continue;
            }
        }
        //Updates interactable objects
        for (int interactableIndex = interactables.size()-1; interactableIndex >= 0; interactableIndex--) {
            interactables.get(interactableIndex).update();
            if (!interactables.get(interactableIndex).alive) {
                interactables.remove(interactableIndex);
                continue;
            }
        }
        //Updates particles
        for (int particleIndex = 0; particleIndex < particles.size(); particleIndex++) {
            particles.get(particleIndex).update();
            if (!particles.get(particleIndex).alive) {
                particles.remove(particleIndex);
                continue;
            }
        }
        //Updates projectiles
        for (int projectileIndex = 0; projectileIndex < projectiles.size(); projectileIndex++) {
            projectiles.get(projectileIndex).update();
            if (!projectiles.get(projectileIndex).alive) {
                projectiles.remove(projectileIndex);
                continue;
            }
        }
        //Updates aoes
        for (int aoeIndex = 0; aoeIndex < aoes.size(); aoeIndex++) {
            aoes.get(aoeIndex).update();
            if (!aoes.get(aoeIndex).alive) {
                aoes.remove(aoeIndex);
                continue;
            }
        }

        //Updates NPCs
        for (int npcIndex = 0; npcIndex < npcs.size(); npcIndex++) {
            npcs.get(npcIndex).update();
            if (!npcs.get(npcIndex).alive) {
                npcs.remove(npcIndex);
                continue;
            }
        }

        //Key-related operations unrelated to any specific game object
        if (keyH.escapePressed == true && gp.getGameState().equals("PLAYING") && !gp.hasChangedMenu) {
            gp.createMenu(101);
        }
        
    }

    public void draw(Graphics2D g2, double GS) {
        //Background
        if (levelBG != null)
            levelBG.drawBackground(g2, GS);

        //Platforms
        for (int i = 0; i < platforms.size(); i++)
            platforms.get(i).draw(g2, GS);
        //Interactables
        for (int i = 0; i < interactables.size(); i++)
            interactables.get(i).draw(g2, GS);
        //Projectiles
        for (int projectileIndex = 0; projectileIndex < projectiles.size(); projectileIndex++) {
            projectiles.get(projectileIndex).draw(g2, GS);
        }
        //Areas of effects (AOEs)
        for (int aoeIndex = 0; aoeIndex < aoes.size(); aoeIndex++) {
            aoes.get(aoeIndex).draw(g2, GS);
        }
        //NPCs
        for (int npcIndex = 0; npcIndex < npcs.size(); npcIndex++) {
            npcs.get(npcIndex).draw(g2, GS);
        }
        //Particles
        for (int particleIndex = 0; particleIndex < particles.size(); particleIndex++) {
            particles.get(particleIndex).draw(g2, GS);
        }

        gp.player.draw(g2, GS);
        levelBG.drawForeground(g2, GS);
        HUD.draw(g2, gp, GS);
    }

    public void levelEvents() {
        for (int flagIndex = 0; flagIndex < flags.size(); flagIndex++) {
            //System.out.println("Flag: "+flags.get(flagIndex));
            if (time > 0) {
                FC.check(flags.get(flagIndex));
            }
        }
    }
}
