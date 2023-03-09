package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import main.GamePanel;

public class SaveLoad {
    private String filePath;
    GamePanel gp;

    public SaveLoad(GamePanel gp) {
        this.gp = gp;
        filePath = System.getProperty("user.dir");
    }

    //Saves current settings to text file, only used in devlopment
    public void saveSettings() {
        System.out.println("Saving settings...");
        try {
            FileWriter saveWriter = new FileWriter(filePath+"/src/data/settings.txt");
            
            //Video settings
            System.out.println("Saving video settings...");
            saveWriter.write("#Video settings");
            saveWriter.write("\nscreen_width = "+gp.screenWidth);
            saveWriter.write("\nscreen_height = "+gp.screenHeight);
            saveWriter.write("\nFPS = "+gp.FPS);
            saveWriter.write("\nis_fullscreen = "+gp.isFullScreen);

            //Audio settings
            System.out.println("Saving audio settings...");
            saveWriter.write("\n\n#Audio settings (in percentage values)");
            saveWriter.write("\nvolume = "+gp.volume);

            //Controls
            System.out.println("Saving controls...");
            saveWriter.write("\n\n#Controls/keybinds");
            saveWriter.write("\njump_code = "+gp.jumpCode);
            saveWriter.write("\nup_code = "+gp.upCode);
            saveWriter.write("\ndown_code = "+gp.downCode);
            saveWriter.write("\nleft_code = "+gp.leftCode);
            saveWriter.write("\nright_code = "+gp.rightCode);
            saveWriter.write("\ninteract_code = "+gp.interactCode);
            saveWriter.write("\ninventory_code = "+gp.inventoryCode);
            saveWriter.write("\nequip_1_code = "+gp.equip1Code);
            saveWriter.write("\nequip_2_code = "+gp.equip2Code);
            saveWriter.write("\nequip_3_code = "+gp.equip3Code);
            saveWriter.write("\nequip_4_code = "+gp.equip4Code);
            saveWriter.write("\nequip_5_code = "+gp.equip5Code);

            saveWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurred while saving");
            e.printStackTrace();
        }
        System.out.println("Done saving settings");
    }

    //Loads settings from settings.txt file
    public void loadSettings() {
        System.out.print("Loading settings");
        try {
            //Creating File using path
            File text = new File(filePath+"/src/data/settings.txt");
     
            //Creating Scanner instance to read text
            Scanner saveScanner = new Scanner(text);
     
            //Reading each line of the file and updating game
            while (saveScanner.hasNextLine()) {
                //Reading input
                String line = saveScanner.nextLine().trim();

                //Ignores everything past '#' (comments/documentation)
                if (line.contains("#"))
                    line = line.substring(0, line.indexOf('#'));

                //Video settings
                else if (line.startsWith("screen_width")) {
                    gp.screenWidth = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
                }
                else if (line.startsWith("screen_height")) {
                    gp.screenHeight = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
                }
                else if (line.startsWith("FPS")) {
                    gp.FPS = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
                }
                else if (line.startsWith("is_fullscreen")) {
                    gp.isFullScreen = Boolean.parseBoolean(line.substring(line.indexOf('=')+1).trim());
                }

                //Audio settings
                else if (line.startsWith("volume")) {
                    gp.volume = Integer.parseInt(line.substring(line.indexOf('=')+1).trim());
                }
                
                System.out.print(".");
            }
            

            saveScanner.close();
        } catch (IOException e) {
            System.out.println("Error occurred while loading");
            e.printStackTrace();
        }
        System.out.println("  Done loading");
    }
}
