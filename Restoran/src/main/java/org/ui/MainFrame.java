/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ui;

/**
 *
 * @author ASUS
 */

import javax.swing.*;
import java.awt.*;
import org.main.*;

public class MainFrame extends JFrame {
    
    //ini otak ui-nya jangan macem2

    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);

    GamePanel gp = new GamePanel();

    public MainFrame() {
        setTitle("Restaurant Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        container.add(new MainMenuPanel(this), "menu");
        

        container.add(new DapurScene(this), "dapur");
        container.add(gp, "restoran");

        add(container);
        
        cardLayout.show(container, "menu");

        setVisible(true);

        gp.startGameThread();
    }

    public void showPanel(String name) {
        cardLayout.show(container, name);

        if (name.equals("restoran")) {
        gp.requestFocusInWindow(); 
    }
}
    
    public GamePanel getGamePanel() {
        return gp;
    }
    
}