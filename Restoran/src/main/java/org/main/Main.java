package org.main;

import javax.swing.JFrame;

import org.ui.GamePanel;
import org.ui.MainFrame;

//public class Main {
//    
//    public static void main(String[] args) {
//
//        JFrame window = new JFrame();
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setResizable(false);
//        window.setTitle("Restaurant Tycoon");
//
//        GamePanel gamePanel = new GamePanel();
//        window.add(gamePanel);
//        window.pack();
//
//        window.setLocationRelativeTo(null);
//        window.setVisible(true);
//
//        //start game thread
//        gamePanel.startGameThread(); 
//    }
//}


public class Main {
    
    //public GamePanel gamePanel = new GamePanel();
    
    public static void main(String[] args) {
        new MainFrame();
    }
}