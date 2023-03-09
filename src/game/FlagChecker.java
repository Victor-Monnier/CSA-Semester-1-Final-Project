package game;

import main.GamePanel;

public class FlagChecker {
    public boolean hasFired = false, isValid = false;
    GamePanel gp;
    Level level;

    public FlagChecker(GamePanel gp, Level level) {
        this.gp = gp;
        this.level = level;
    }

    //Checks conditions which are usually level specific
    public void check(String ID) {
        switch (ID) {
            case "1":
                //Moves platform 1 down when player steps on it
                for (Platform platform : level.platforms) {
                    if (platform.ID == 1) {
                        if (platform.y < 1950) {
                            if (gp.player.x >= 3800 && gp.player.x+gp.player.width <= 4000) {
                                platform.ySpeed = 4;
                                if (platform.y-gp.player.y < gp.player.height+platform.ySpeed+1)
                                    gp.player.y = platform.y-gp.player.height+platform.ySpeed;
                            }
                        }
                        else {
                            platform.y = 1950;
                            platform.xSpeed = 0;
                            platform.ySpeed = 0;
                        }
                    }
                }
                break;
            case "2":
                //Moves platform 2 down when the player is in the nook to the top left
                for (Platform platform : level.platforms) {
                    if (platform.ID == 2) {
                        if (platform.y < 1100) {
                            if (gp.player.x == 5040 && gp.player.y == 780)
                                platform.ySpeed = 1;
                        }
                        else {
                            platform.y = 1100;
                            platform.xSpeed = 0;
                            platform.ySpeed = 0;
                        }
                    
                    }
                }
                break;
            case "trial":
                //Spawns enemies until the player dies
                if (gp.levelID == 2 && gp.player.y > 2000) {
                    gp.timeInTrial++;
                    if (level.time % 60 == 0) {
                        int randomCode = (int) (Math.random()*10+1);
                        System.out.println(randomCode);
                        if (randomCode <= 3) {
                            int[] spriteIDs = {10, 11, 12, 13};
                            gp.level.addNPC(1100, 2200, "meleeEnemy1", spriteIDs);
                        }
                        else if (randomCode == 4) {
                            int[] spriteIDs = {10, 11, 12, 13};
                            gp.level.addNPC(1100, 2200, "meleeEnemy2", spriteIDs);
                        }
                        else if (randomCode <= 7) {
                            //Spawning on left vs. right
                            int[] spriteIDs = {5, 8};
                            if (gp.level.time % 120 == 0)
                                gp.level.addNPC(100, (int) (Math.random()*1001+2200), "turret4", spriteIDs);
                            else
                                gp.level.addNPC(2000, (int) (Math.random()*1001+2200), "turret4", spriteIDs);
                        }
                        else {
                            //Spawning on left vs. right
                            int[] spriteIDs = {5, 15};
                            if (gp.level.time % 120 == 0)
                                gp.level.addNPC(100, (int) (Math.random()*1001+2200), "turret5", spriteIDs);
                            else
                                gp.level.addNPC(2000, (int) (Math.random()*1001+2200), "turret5", spriteIDs);
                        }
                    }
                }
                break;
        }
    }
}
