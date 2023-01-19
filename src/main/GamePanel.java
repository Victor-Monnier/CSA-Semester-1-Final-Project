package main;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import game.*;
import data.*;
import gui.MenuGUI;

public class GamePanel extends JPanel implements Runnable {
    //Game Globals
    public boolean isFullScreen = false, hasChangedMenu = false, loadingGraphics = false,
    mouseLeftClicked = false, mouseLeftPressed = false, mouseRightClicked = false, mouseRightPressed = false, mouseScrolled = false;
    public int levelID = 1, menuID = 0, 
    xMovement = 0, yMovement = 0,
    mouseX = 0, mouseY = 0, mouseAngle, mouseScrollAmount,
    timeInTrial;
    public enum GameState {PLAYING, INMENU, PAUSED, INGAMEMENU}
    public GameState gameState = GameState.PLAYING;

    //Settings
    public int 
    screenWidth = 1600, /* 16:9 Aspect Ratio (fixed) */
    screenHeight = 900,
    FPS = 256, /* Amount of graphical updates per second */
    volume = 50;
    public double GS = screenHeight/900.0; /* graphics scaling: all graphics are same relative size and position regardless of screen size */

    //Preloads commonly used sprites. This only works when a limited amount of graphics
    public BufferedImage    main_menu_background, menu_background_1, //Backgrounds
                            gui_button_1, gui_button_1_selected, gui_item_slot, gui_item_slot_selected, //GUI
                            coin, heart, heart_plus, crystal, crystal_plus,
                            player_down_1, player_down_2, player_left_1, player_left_2, player_right_1, player_right_2, player_up_1, player_up_2, //Player
                            stick_left, stick_right, magic_stick_left, magic_stick_right, fire_stick_left, fire_stick_right, power_stick_left, power_stick_right, lifeSteal_stick_left, lifeSteal_stick_right, //Items
                            particle_1, //Particle
                            projectile_1, projectile_2, projectile_3, //Projectiles
                            hit_box; //Used for testing
    public void getCommonImages() {
        try {
            main_menu_background = ImageIO.read(getClass().getResourceAsStream("/res/background/menu/main_menu.jpg"));
            menu_background_1 = ImageIO.read(getClass().getResourceAsStream("/res/background/menu/menu_1.png"));
            gui_button_1 = ImageIO.read(getClass().getResourceAsStream("/res/gui/button_1.jpg"));
            gui_button_1_selected = ImageIO.read(getClass().getResourceAsStream("/res/gui/button_1_selected.jpg"));
            gui_item_slot = ImageIO.read(getClass().getResourceAsStream("/res/gui/item_slot.png"));
            gui_item_slot_selected = ImageIO.read(getClass().getResourceAsStream("/res/gui/item_slot_selected.png"));
            coin = ImageIO.read(getClass().getResourceAsStream("/res/icon/coin.png"));
            heart = ImageIO.read(getClass().getResourceAsStream("/res/icon/heart.png"));
            heart_plus = ImageIO.read(getClass().getResourceAsStream("/res/icon/heart_plus.png"));
            crystal = ImageIO.read(getClass().getResourceAsStream("/res/icon/crystal.png"));
            crystal_plus = ImageIO.read(getClass().getResourceAsStream("/res/icon/crystal_plus.png"));
            player_down_1 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_down_1.png"));
            player_down_2 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_down_2.png"));
            player_left_1 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_left_1.png"));
            player_left_2 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_left_2.png"));
            player_right_1 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_right_1.png"));
            player_right_2 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_right_2.png"));
            player_up_1 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_up_1.png"));
            player_up_2 = ImageIO.read(getClass().getResourceAsStream("/res/entity/player/player_up_2.png"));
            stick_left = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/stick_left.png"));
            stick_right = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/stick_right.png"));
            magic_stick_left = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/magic_stick_left.png"));
            magic_stick_right = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/magic_stick_right.png"));
            fire_stick_left = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/fire_stick_left.png"));
            fire_stick_right = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/fire_stick_right.png"));
            power_stick_left = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/power_stick_left.png"));
            power_stick_right = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/power_stick_right.png"));
            lifeSteal_stick_left = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/life-steal_stick_left.png"));
            lifeSteal_stick_right = ImageIO.read(getClass().getResourceAsStream("/res/game_object/item/life-steal_stick_right.png"));
            particle_1 = ImageIO.read(getClass().getResourceAsStream("/res/game_object/particle/particle_1.png"));
            projectile_1 = ImageIO.read(getClass().getResourceAsStream("/res/game_object/projectile/projectile_1.png"));
            projectile_2 = ImageIO.read(getClass().getResourceAsStream("/res/game_object/projectile/projectile_2.png"));
            projectile_3 = ImageIO.read(getClass().getResourceAsStream("/res/game_object/projectile/projectile_3.png"));
            hit_box = ImageIO.read(getClass().getResourceAsStream("/res/wip/hit_box.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Keyboard
    public KeyHandler keyH = new KeyHandler();
    //Codes for actions
    public String jumpCode = "SPACE", upCode = "W", downCode = "S", leftCode = "A", rightCode = "D", interactCode = "R", inventoryCode = "E", equip1Code = "1", equip2Code = "2", equip3Code = "3", equip4Code = "4", equip5Code = "5";
    //Keys for menu interaction
    Action upArrowPressedAction = keyH.new UpArrowPressedAction();
    Action upArrowReleasedAction = keyH.new UpArrowReleasedAction();
    Action downArrowPressedAction = keyH.new DownArrowPressedAction();
    Action downArrowReleasedAction = keyH.new DownArrowReleasedAction();
    Action leftArrowPressedAction = keyH.new LeftArrowPressedAction();
    Action leftArrowReleasedAction = keyH.new LeftArrowReleasedAction();
    Action rightArrowPressedAction = keyH.new RightArrowPressedAction();
    Action rightArrowReleasedAction = keyH.new RightArrowReleasedAction();
    Action enterPressedAction = keyH.new EnterPressedAction();
    Action enterReleasedAction = keyH.new EnterReleasedAction();
    Action escapePressedAction = keyH.new EscapePressedAction();
    Action escapeReleasedAction = keyH.new EscapeReleasedAction();
    //Movement
    Action jumpPressedAction = keyH.new JumpPressedAction();
    Action jumpReleasedAction = keyH.new JumpReleasedAction();
    Action upPressedAction = keyH.new UpPressedAction();
    Action upReleasedAction = keyH.new UpReleasedAction();
    Action downPressedAction = keyH.new DownPressedAction();
    Action downReleasedAction = keyH.new DownReleasedAction();
    Action leftPressedAction = keyH.new LeftPressedAction();
    Action leftReleasedAction = keyH.new LeftReleasedAction();
    Action rightPressedAction = keyH.new RightPressedAction();
    Action rightReleasedAction = keyH.new RightReleasedAction();
    //Misc
    Action interactPressedAction = keyH.new InteractPressedAction();
    Action interactReleasedAction = keyH.new InteractReleasedAction();
    Action equip1PressedAction = keyH.new equip1PressedAction();
    Action equip2PressedAction = keyH.new equip2PressedAction();
    Action equip3PressedAction = keyH.new equip3PressedAction();
    Action equip4PressedAction = keyH.new equip4PressedAction();
    Action equip5PressedAction = keyH.new equip5PressedAction();
    //Used for setting keybindings
    Action qAction = keyH.new QAction(); //First row
    Action wAction = keyH.new WAction();
    Action eAction = keyH.new EAction();
    Action rAction = keyH.new RAction();
    Action tAction = keyH.new TAction();
    Action yAction = keyH.new YAction();
    Action uAction = keyH.new UAction();
    Action iAction = keyH.new IAction();
    Action oAction = keyH.new OAction();
    Action pAction = keyH.new PAction();
    Action aAction = keyH.new AAction(); //Second row
    Action sAction = keyH.new SAction();
    Action dAction = keyH.new DAction();
    Action fAction = keyH.new FAction();
    Action gAction = keyH.new GAction();
    Action hAction = keyH.new HAction();
    Action jAction = keyH.new JAction();
    Action kAction = keyH.new KAction();
    Action lAction = keyH.new LAction();
    Action zAction = keyH.new ZAction(); //Third row
    Action xAction = keyH.new XAction();
    Action cAction = keyH.new CAction();
    Action vAction = keyH.new VAction();
    Action bAction = keyH.new BAction();
    Action nAction = keyH.new NAction();
    Action mAction = keyH.new MAction();
    //JComponent that the keybinds are added to
    JComponent thisWindow = Main.window.getRootPane();
    public void updateKeyBindings() {
        //Keys for menu interaction
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upArrowPressed");
		thisWindow.getActionMap().put("upArrowPressed", upArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released UP"), "upArrowReleased");
		thisWindow.getActionMap().put("upArrowReleased", upArrowReleasedAction);
		thisWindow.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downArrowPressed");
		thisWindow.getActionMap().put("downArrowPressed", downArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "downArrowReleased");
		thisWindow.getActionMap().put("downArrowReleased", downArrowReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftArrowPressed");
		thisWindow.getActionMap().put("leftArrowPressed", leftArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "leftArrowReleased");
		thisWindow.getActionMap().put("leftArrowReleased", leftArrowReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightArrowPressed");
		thisWindow.getActionMap().put("rightArrowPressed", rightArrowPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "rightArrowReleased");
		thisWindow.getActionMap().put("rightArrowReleased", rightArrowReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "enterPressed");
		thisWindow.getActionMap().put("enterPressed", enterPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released ENTER"), "enterReleased");
		thisWindow.getActionMap().put("enterReleased", enterReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escapePressed");
		thisWindow.getActionMap().put("escapePressed", escapePressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released ESCAPE"), "escapeReleased");
		thisWindow.getActionMap().put("escapeReleased", escapeReleasedAction);
        //Movement
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(jumpCode), "jumpPressed");
		thisWindow.getActionMap().put("jumpPressed", jumpPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released "+jumpCode), "jumpReleased");
		thisWindow.getActionMap().put("jumpReleased", jumpReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(upCode), "upPressed");
		thisWindow.getActionMap().put("upPressed", upPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released "+upCode), "upReleased");
		thisWindow.getActionMap().put("upReleased", upReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(downCode), "downPressed");
		thisWindow.getActionMap().put("downPressed", downPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released "+downCode), "downReleased");
		thisWindow.getActionMap().put("downReleased", downReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(leftCode), "leftPressed");
		thisWindow.getActionMap().put("leftPressed", leftPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released "+leftCode), "leftReleased");
		thisWindow.getActionMap().put("leftReleased", leftReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(rightCode), "rightPressed");
		thisWindow.getActionMap().put("rightPressed", rightPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released "+rightCode), "rightReleased");
		thisWindow.getActionMap().put("rightReleased", rightReleasedAction);
        //Misc
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(interactCode), "interactPressed");
		thisWindow.getActionMap().put("interactPressed", interactPressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("released "+interactCode), "interactReleased");
		thisWindow.getActionMap().put("interactReleased", interactReleasedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(equip1Code), "equip1Pressed");
		thisWindow.getActionMap().put("equip1Pressed", equip1PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(equip2Code), "equip2Pressed");
		thisWindow.getActionMap().put("equip2Pressed", equip2PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(equip3Code), "equip3Pressed");
		thisWindow.getActionMap().put("equip3Pressed", equip3PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(equip4Code), "equip4Pressed");
		thisWindow.getActionMap().put("equip4Pressed", equip4PressedAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke(equip5Code), "equip5Pressed");
		thisWindow.getActionMap().put("equip5Pressed", equip5PressedAction);
    }
    //Used for setting keybindings
    public void createKeyStrokeDetectors() {
        //Moves left to right, top to bottom on standard keyboard
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("Q"), "qAction");
		thisWindow.getActionMap().put("qAction", qAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("W"), "wAction");
		thisWindow.getActionMap().put("wAction", wAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("E"), "eAction");
		thisWindow.getActionMap().put("eAction", eAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("R"), "rAction");
		thisWindow.getActionMap().put("rAction", rAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("T"), "tAction");
		thisWindow.getActionMap().put("tAction", tAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("Y"), "yAction");
		thisWindow.getActionMap().put("yAction", yAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("U"), "uAction");
		thisWindow.getActionMap().put("uAction", uAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("I"), "iAction");
		thisWindow.getActionMap().put("iAction", iAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("O"), "oAction");
		thisWindow.getActionMap().put("oAction", oAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("P"), "pAction");
		thisWindow.getActionMap().put("pAction", pAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("A"), "aAction");
		thisWindow.getActionMap().put("aAction", aAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("S"), "sAction");
		thisWindow.getActionMap().put("sAction", sAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("D"), "dAction");
		thisWindow.getActionMap().put("dAction", dAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("F"), "fAction");
		thisWindow.getActionMap().put("fAction", fAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("G"), "gAction");
		thisWindow.getActionMap().put("gAction", gAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("H"), "hAction");
		thisWindow.getActionMap().put("hAction", hAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("J"), "jAction");
		thisWindow.getActionMap().put("jAction", jAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("K"), "kAction");
		thisWindow.getActionMap().put("kAction", kAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("L"), "lAction");
		thisWindow.getActionMap().put("lAction", lAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("Z"), "zAction");
		thisWindow.getActionMap().put("zAction", zAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("X"), "xAction");
		thisWindow.getActionMap().put("xAction", xAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("C"), "cAction");
		thisWindow.getActionMap().put("cAction", cAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("V"), "vAction");
		thisWindow.getActionMap().put("vAction", vAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("B"), "bAction");
		thisWindow.getActionMap().put("bAction", bAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("N"), "nAction");
		thisWindow.getActionMap().put("nAction", nAction);
        thisWindow.getInputMap().put(KeyStroke.getKeyStroke("M"), "mAction");
		thisWindow.getActionMap().put("mAction", mAction);
    }
    //Clears keybinds for setting keys
    public void resetKeyBindings() {
        thisWindow.getActionMap().put("qAction", null);
        thisWindow.getActionMap().put("wAction", null);
        thisWindow.getActionMap().put("eAction", null);
        thisWindow.getActionMap().put("rAction", null);
        thisWindow.getActionMap().put("tAction", null);
        thisWindow.getActionMap().put("yAction", null);
        thisWindow.getActionMap().put("uAction", null);
        thisWindow.getActionMap().put("iAction", null);
        thisWindow.getActionMap().put("oAction", null);
        thisWindow.getActionMap().put("pAction", null);
        thisWindow.getActionMap().put("aAction", null);
        thisWindow.getActionMap().put("sAction", null);
        thisWindow.getActionMap().put("dAction", null);
        thisWindow.getActionMap().put("fAction", null);
        thisWindow.getActionMap().put("gAction", null);
        thisWindow.getActionMap().put("hAction", null);
        thisWindow.getActionMap().put("jAction", null);
        thisWindow.getActionMap().put("kAction", null);
        thisWindow.getActionMap().put("lAction", null);
        thisWindow.getActionMap().put("zAction", null);
        thisWindow.getActionMap().put("xAction", null);
        thisWindow.getActionMap().put("cAction", null);
        thisWindow.getActionMap().put("vAction", null);
        thisWindow.getActionMap().put("bAction", null);
        thisWindow.getActionMap().put("nAction", null);
        thisWindow.getActionMap().put("mAction", null);
        
        updateKeyBindings();
    }

    //Mouse
    MouseHandler mouseH = new MouseHandler(this, GS);
    Sound sound = new Sound();
    Thread gameThread;
    public Player player;

    //Level which controls what happens specifically while the game runs
    public Level level;
    //Loads specific level from ID. If levelID <= 0, it is related to menus.
    public void createLevel(int ID) {
        gameState = GameState.PLAYING;
        this.levelID = ID;
        //Creates level
        level = new Level(this, keyH, levelID);
    }

    //Menus, specifically (not just text on screen)
    MenuGUI menuGUI;
    //The GUI
    public void createMenu(int menuID) {
        this.menuID = menuID;
        //Creates menu, which also updates game state
        menuGUI = new MenuGUI(this);
    }

    //Used for saving and loading data
    public SaveLoad saveLoad = new SaveLoad(this);

    //Used in other classes where GameState can't be accessed
    public void setGameState(String thisGameState) {
        switch (thisGameState) {
            case "PLAYING":
                gameState = GameState.PLAYING;
                break;
            case "INMENU":
                gameState = GameState.INMENU;
                break;
            case "PAUSED":
                gameState = GameState.PAUSED;
                break;
            case "INGAMEMENU":
                gameState = GameState.INGAMEMENU;
                break;
        }
    }
    //Used in other classes where GameState can't be accessed
    public String getGameState() {
        switch (gameState) {
            case PLAYING:
                return "PLAYING";
            case INMENU:
                return "INMENU";
            case PAUSED:
                return "PAUSED";
            case INGAMEMENU:
                return "INGAMEMENU";
        }
        return("ERROR: FAULTY GAMESTATE");
    }

    public GamePanel() {
        //System stuff
        getCommonImages();
        createMenu(0); //Starts on main menu

        //Loading/updating settings
        //saveLoad.saveSettings();
        saveLoad.loadSettings();
        updateKeyBindings();

        //Game related        
        player = new Player(this, keyH, 200, 200);;
        playSF(0);
        
        //Scaling for fullscreen requires additional information, so it comes last
        if (isFullScreen)
            setFullScreen();

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
        this.addMouseWheelListener(mouseH);
        this.setFocusable(true);
    }

    public void setFullScreen() {
        //Getting the width and height of RL screen
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //Setting the JFrame to have the proper resolution
        screenWidth = 2560;//ge.getMaximumWindowBounds().width;
        screenHeight = 1440;//ge.getMaximumWindowBounds().height;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        GS = screenHeight/900.0;
        //Resets the mouse handler
        mouseH.GS = GS;

        //Setting the JFrame to be full screen
        gd.setFullScreenWindow(Main.window);

        update();
        repaint();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Game loop
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double drawDelta = 0;
        double updateInterval = 1000000000/60;
        double updateDelta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long drawTimer = 0;
        long updateTimer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            drawDelta += (currentTime-lastTime)/drawInterval;
            updateDelta += (currentTime-lastTime)/updateInterval;
            drawTimer += (currentTime-lastTime);
            updateTimer += (currentTime-lastTime);
            lastTime = currentTime;

            //Logical updates
            if (updateDelta >= 1) {
                update();
                updateDelta--;
            }

            //Graphical updates
            if (drawDelta >= 1) {
                if (!loadingGraphics) {
                    repaint();
                }
                drawDelta--;
                drawCount++;
            }

            if (drawTimer >= 1000000000) {
                System.out.println("FPS: "+drawCount);
                drawCount = 0;
                drawTimer = 0;
            }
            if (updateTimer >= 1000000000) {
                updateTimer = 0;
            }
        }
    }

    //Logcial updates
    public void update() {
        switch (gameState) {
            case INMENU:
            case PAUSED:
                menuGUI.update();
                break;
            case INGAMEMENU:
                player.canInteract = false;
                menuGUI.update();
            case PLAYING:
                player.update();
                level.update();
                break;
        }

        //Updates mouse variables
        mouseLeftPressed = mouseH.mouseLeftPressed;
        mouseLeftClicked = mouseH.mouseLeftClicked;
        mouseRightPressed = mouseH.mouseRightPressed;
        mouseRightClicked = mouseH.mouseRightClicked;
        mouseScrolled = mouseH.mouseScrolled;
        mouseX = mouseH.mouseX;
        mouseY = mouseH.mouseY;
        mouseScrollAmount = mouseH.mouseScrollAmount;

        //The angle from the mouse position to the player
        if (mouseX<player.onScreenX+player.width/2) {
            mouseAngle = 180 + (int) Math.toDegrees(Math.atan((-mouseY+player.onScreenY+player.height/2)/(mouseX-player.onScreenX-player.width/2+0.00001)));
        }
        else {
            mouseAngle = (int) Math.toDegrees(Math.atan((-mouseY+player.onScreenY+player.height/2)/(mouseX-player.onScreenX-player.width/2+0.00001)));
        }

        //Resets variables that only last for one update
        hasChangedMenu = false;
        mouseH.mouseLeftClicked = false;
        mouseH.mouseRightClicked = false;
        mouseH.mouseScrolled = false;
        mouseH.mouseScrollAmount = 0;
        keyH.escapePressed = false;
        keyH.enterPressed = false;
        keyH.anyKeyPressed = false;
        keyH.lastKeyPressed = null;
    }

    //Graphical updates
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //Anti aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); 

        //Draws canvas based on game state
        switch (gameState) {
            case PLAYING:
                if (level != null)
                    level.draw(g2, GS);
                break;
            case PAUSED:
            case INGAMEMENU:
                if (level != null)
                    level.draw(g2, GS);
            case INMENU:
                menuGUI.draw(g2, GS);
                break;
        }

        g2.dispose();
    }

    //All sound related stuff
    public void playSF(int i) {
        sound.setFile(i);
        setSFVolume(volume);
        sound.play();
    }
    public void setSFVolume(int SFVolume) {
        volume = SFVolume;
        if (volume > 100)
            volume = 100;
        else if (volume < 0)
            volume = 0;
        sound.setVolume(volume);
    }
}
