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

public class DapurScene extends JPanel {
    //scene buat dapur, blom gw bikin

    public DapurScene(MainFrame frame) {
        
        setLayout(new BorderLayout());
        setBackground(Color.LIGHT_GRAY);

        JLabel title = new JLabel("Blom ada UI-nya, faqih tolonggg", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton startBtn = new JButton("Buka Restoran");

        startBtn.addActionListener(e -> {
            frame.showPanel("restoran");
            
            GamePanel gp = frame.getGamePanel();
            gp.gm.mulaiFasePenjualan();
        });

        add(title, BorderLayout.NORTH);
        add(startBtn, BorderLayout.SOUTH);
        
    }
}