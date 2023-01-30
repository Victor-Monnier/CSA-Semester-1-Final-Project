///////////////////////////////////////
//  |         |     -----     |---\  //
//  |    |    |       |       |   |  //
//  |    |    |       |       |---/  //
//   \__/ \__/      -----     |      //
///////////////////////////////////////

package main;

import javax.swing.*;

public class Main{

    public static JFrame window = new JFrame();

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        //Setting properties of window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Victor Monnier CSA Final Project Semester 1");
        window.setFocusTraversalKeysEnabled(false); 

        //Adding gamePanel, which actually controls the application
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}

