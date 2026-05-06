package org.ui;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    
    // CardLayout digunakan untuk menumpuk panel dan menggantinya dengan mudah
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public MainFrame() {
        // Pindahkan settingan window dari kode Main lamamu ke sini
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Restaurant Tycoon");

        // Inisialisasi Panel-Panel
        GamePanel gamePanel = new GamePanel();
        // MainMenuPanel mainMenu = new MainMenuPanel(this); // Contoh panel lain

        // Masukkan semua panel ke dalam wadah utama
        mainContainer.add(gamePanel, "GAME_PANEL");
        // mainContainer.add(mainMenu, "MAIN_MENU");

        this.add(mainContainer);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Jalankan game
        gamePanel.startGameThread(); 
    }

    // Metode untuk pindah layar (misal dari menu ke game)
    public void switchPanel(String name) {
        cardLayout.show(mainContainer, name);
    }
}