package org.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.entity.NPC;
import org.entity.Player;
import org.main.GameManager;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Entity & Manager
    Player player = new Player(this, keyHandler);
    GameManager gm = new GameManager(this);
    
    public GameManager getGameManager() {
        return gm;
    }

    int detikBerjalan = 0; // Waktu dalam detik
    int pembeliHariIni = 0;
    ArrayList<NPC> listNPC = new ArrayList<>();
    public Object restaurantScene;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void spawnNPC() {
        listNPC.add(new NPC(this));
        pembeliHariIni++;
        System.out.println("NPC Baru muncul!");
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / 60; // 60 FPS
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0; // Untuk menghitung FPS dan detik

        // Game loop
        while (gameThread != null) {
            currentTime = System.nanoTime();
            long passedTime = currentTime - lastTime; // Selisih waktu
            
            delta += passedTime / drawInterval;
            timer += passedTime; 
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            // Jika timer mencapai 1 detik (1 miliar nanodetik)
            if (timer >= 1000000000) {
                if (gm.sedangBerjualan) {
                    detikBerjalan++; // Tambah detik hanya saat toko buka
                    gm.updateWaktu(detikBerjalan);
                }
                timer = 0; // Reset timer setiap detik
            }
        }
    }

    public void update() {
        player.update();

        if (keyHandler.enterPressed && !gm.sedangBerjualan) {
            System.out.println("--- MEMULAI HARI BARU (TES) ---");
            detikBerjalan = 0; // Reset waktu ke 0
            pembeliHariIni = 0; // Reset jumlah pembeli hari ini
            gm.mulaiFasePenjualan(); // GameManager menghitung jadwal NPC
        }
        // Update semua NPC yang ada di list
        for (int i = 0; i < listNPC.size(); i++) {
            if(listNPC.get(i).isAktif()){
                listNPC.get(i).update(gm.restaurantScene);
            }else{
                listNPC.remove(i);
                i--;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Gambar background level
        if (gm != null && gm.restaurantScene != null) {
            gm.restaurantScene.drawBackground(g2, screenWidth, screenHeight);
        }

        // kasir (sementara)
        g2.setColor(Color.YELLOW);
        g2.fillRect(100, 200, 16, 16);

        // Gambar Player
        player.draw(g2);

        // Gambar NPC
        for (NPC npc : listNPC) {
            npc.draw(g2); // Meminta tiap NPC menggambar dirinya sendiri
        }

        // Tampilkan Status Waktu di pojok layar
        g2.setColor(Color.WHITE);
        g2.drawString("Waktu: " + detikBerjalan + "s", 20, 50);
        g2.drawString("NPC di Layar: " + listNPC.size(), 20, 70);
        g2.drawString("NPC Hari Ini: " + pembeliHariIni, 20, 90);
        if(!gm.sedangBerjualan) g2.drawString("TEKAN ENTER UNTUK BUKA", 300, 300);
        
        g2.dispose();
    }
}