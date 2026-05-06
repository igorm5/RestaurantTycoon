/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.ui;

import javax.swing.*;
import java.awt.*;


public class MainMenuPanel extends JPanel {

    private Image backgroundImage;

    public MainMenuPanel(MainFrame frame) {
        setLayout(null);

        backgroundImage = new ImageIcon(
                getClass().getResource("/menupanel/menupanel.jpeg")
        ).getImage();

        ImageIcon startIcon = new ImageIcon(
                getClass().getResource("/button/start.png")
        );

        JButton startBtn = new JButton(startIcon);
        
        setupImageButton(startBtn, "/button/start.png");

        startBtn.addActionListener(e -> {
            frame.showPanel("dapur");
        });
        
        JButton loadBtn = new JButton();
        
        setupImageButton(loadBtn, "/button/load.png");
        
        loadBtn.addActionListener(e -> {
            System.out.println("Load game...");
            // nanti isi logic load di sini, aku gatau maaf ya igor
        });
        
        JLabel title = new JLabel("RESTAURANT TYCOON");
        title.setForeground(Color.WHITE);
        title.setFont(loadFont(40f));

        title.setForeground(Color.WHITE);
        title.setBounds(50, 200, 1000, 40); // x, y, width, height
        add(title);
        
        startBtn.setBounds(275, 250, 220, 80);
        add(startBtn);
        
        loadBtn.setBounds(275, 270, 220, 160);
        add(loadBtn);

    }
    
    private void setupImageButton(JButton button, String path) {

        ImageIcon originalIcon = new ImageIcon(
            getClass().getResource(path)
        );

        int targetWidth = 220;

        int originalW = originalIcon.getIconWidth();
        int originalH = originalIcon.getIconHeight();

        // hitung tinggi biar rasio tetap
        int targetHeight = (int) ((double) originalH / originalW * targetWidth);

        // ukuran normal
        Image normalImg = originalIcon.getImage()
            .getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        // ukuran pas hover (lebih kecil)
        Image hoverImg = originalIcon.getImage()
            .getScaledInstance((int)(targetWidth * 0.9), (int)(targetHeight * 0.9), Image.SCALE_SMOOTH);

        ImageIcon normalIcon = new ImageIcon(normalImg);
        ImageIcon hoverIcon = new ImageIcon(hoverImg);

        button.setIcon(normalIcon);

        // buat ngikut size hasil scaling
        button.setBounds(button.getX(), button.getY(), targetWidth, targetHeight);

        // styling
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);

        // hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setIcon(hoverIcon);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setIcon(normalIcon);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    private Font loadFont(float size) {
        try {
            Font font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().getResourceAsStream("/fonts/PressStart.ttf")
            );
            return font.deriveFont(size);
        } catch (Exception e) {
            e.printStackTrace();
            return new Font("Arial", Font.BOLD, (int) size); // fallback
        }
    }
}