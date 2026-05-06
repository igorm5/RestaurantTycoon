package org.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.awt.*;

import javax.swing.JPanel;

import org.entity.NPC;
import org.entity.Player;
import org.main.GameManager;
import org.entity.Tikus;
import org.model.EfekTikusLapar;


public class GamePanel extends JPanel implements Runnable {
    
    //maaf klo jadi berantakan tapi kode yg jdi komen dan komen2nya jangan dihapus soalnya sapa tau aku butuh :(
    
    // Screen settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    //public ArrayList<Tikus> listTikus = new ArrayList<>();
    public boolean showTikusWarning = false;
    private Image tikusWarningImage;
    public boolean isShaking = false;
    private int shakeDuration = 0;
    private int shakeOffsetX = 0;
    private int shakeOffsetY = 0;

    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;

    // Entity & Manager
    Player player = new Player(this, keyHandler);
    public GameManager gm = new GameManager(this);
    
    public int detikBerjalan = 0; // Waktu dalam detik
    ArrayList<NPC> listNPC = new ArrayList<>();
    public Object restaurantScene;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        
        tikusWarningImage = new ImageIcon(
            getClass().getResource("/bencana/tikus.png")
        ).getImage();
    }

    public void spawnNPC() {
        listNPC.add(new NPC(this));
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
                gm.mulaiFasePenjualan(); // GameManager menghitung jadwal NPC
            }
            // Update semua NPC yang ada di list
            for (int i = 0; i < listNPC.size(); i++) {
                listNPC.get(i).update(gm.restaurantScene);

                // if (!listNPC.get(i).isAktif()) {
                //     listNPC.remove(i);
                //     i--;
                // }
            }
            
        
        
//        for (int i = 0; i < listTikus.size(); i++) {
//            Tikus t = listTikus.get(i);
//            t.update();
//
//            if (t.x > screenWidth) {
//                listTikus.remove(i);
//                i--;
//            }
//        }
        
        if (isShaking) {
            shakeOffsetX = (int)(Math.random() * 10 - 5); // -5 sampai 5
            shakeOffsetY = (int)(Math.random() * 10 - 5);

            shakeDuration--;

            if (shakeDuration <= 0) {
                isShaking = false;
                shakeOffsetX = 0;
                shakeOffsetY = 0;
            }
        }
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        g2.translate(shakeOffsetX, shakeOffsetY);

        // kasir (sementara)
        g2.setColor(Color.YELLOW);
        g2.fillRect(100, 200, 16, 16);

        // Gambar Player
        player.draw(g2);

        // Gambar NPC
        for (NPC npc : listNPC) {
            npc.draw(g2); // Meminta tiap NPC menggambar dirinya sendiri
        }
        
//        for (Tikus t : listTikus) {
//            t.draw(g2);
//        }

        // Tampilkan Status Waktu di pojok layar
        g2.setColor(Color.WHITE);
        g2.drawString("Waktu: " + detikBerjalan + "s", 20, 50);
        g2.drawString("NPC di Layar: " + listNPC.size(), 20, 70);
        if(!gm.sedangBerjualan) g2.drawString("TEKAN ENTER UNTUK BUKA", 300, 300);
        
        if (showTikusWarning && tikusWarningImage != null) {
            g2.drawImage(tikusWarningImage, 0, 0, screenWidth, screenHeight, null);
        }
        
        g2.dispose();
        
    }
    
//    public void spawnTikus() {
//        listTikus.add(new Tikus(this, new EfekTikusLapar()));
//    }
    
    public void showTikusWarning() {
        showTikusWarning = true;
        Toolkit.getDefaultToolkit().beep();

        // hilang setelah 2 detik
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            showTikusWarning = false;
        }).start();
    }
    
    public void startShake(int duration) {
        isShaking = true;
        shakeDuration = duration;
    }
}