package org.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RestaurantScene {

    private BufferedImage latarImg;

    public class Kursi {
        private int x, y;
        public boolean isKosong;

        public Kursi(int x, int y) {
            this.x = x;
            this.y = y;
            this.isKosong = true;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public int resepsionisX = 100;
    public int resepsionisY = 200;
    private int pintuX;
    private int pintuY;

    public ArrayList<Kursi> daftarKursi = new ArrayList<>();

    public void loadLevel(int level) {
        daftarKursi.clear(); // Hapus kursi level sebelumnya

        if (level == 1) {
            daftarKursi.add(new Kursi(200, 300));
            daftarKursi.add(new Kursi(400, 300));
            setPintu(50, 50);
        } else if (level == 2) {
            daftarKursi.add(new Kursi(150, 200));
            daftarKursi.add(new Kursi(350, 200));
            daftarKursi.add(new Kursi(150, 450));
            daftarKursi.add(new Kursi(350, 450));
            setPintu(50, 50);
        } else if (level == 3) {
            daftarKursi.add(new Kursi(150, 200));
            daftarKursi.add(new Kursi(350, 200));
            daftarKursi.add(new Kursi(550, 200));
            daftarKursi.add(new Kursi(150, 400));
            daftarKursi.add(new Kursi(350, 400));
            daftarKursi.add(new Kursi(550, 400));
            setPintu(50, 50);
        } else if (level == 4) {
            daftarKursi.add(new Kursi(100, 180));
            daftarKursi.add(new Kursi(300, 180));
            daftarKursi.add(new Kursi(500, 180));
            daftarKursi.add(new Kursi(100, 360));
            daftarKursi.add(new Kursi(300, 360));
            daftarKursi.add(new Kursi(500, 360));
            daftarKursi.add(new Kursi(250, 280));
            setPintu(50, 50);
        } else if (level == 5) {
            daftarKursi.add(new Kursi(120, 160));
            daftarKursi.add(new Kursi(320, 160));
            daftarKursi.add(new Kursi(520, 160));
            daftarKursi.add(new Kursi(120, 320));
            daftarKursi.add(new Kursi(320, 320));
            daftarKursi.add(new Kursi(520, 320));
            daftarKursi.add(new Kursi(220, 440));
            daftarKursi.add(new Kursi(420, 440));
            setPintu(50, 50);
        } else {
            setPintu(50, 50);
        }

        loadBackground(level);
    }

    private void loadBackground(int level) {
        try {
            String resourcePath = "/levels/level" + level + ".png";
            latarImg = ImageIO.read(getClass().getResourceAsStream(resourcePath));
            if (latarImg == null) {
                throw new IOException("Resource tidak ditemukan: " + resourcePath);
            }
        } catch (IOException | IllegalArgumentException e) {
            latarImg = createPlaceholderBackground(level);
            System.err.println("Tidak dapat load background level " + level + ", menggunakan placeholder.");
        }
    }

    private BufferedImage createPlaceholderBackground(int level) {
        int width = 768;
        int height = 576;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        Color bgColor;
        switch (level) {
            case 1 -> bgColor = new Color(58, 112, 179);
            case 2 -> bgColor = new Color(143, 116, 75);
            case 3 -> bgColor = new Color(101, 149, 90);
            case 4 -> bgColor = new Color(126, 85, 163);
            case 5 -> bgColor = new Color(170, 76, 98);
            default -> bgColor = new Color(80, 80, 80);
        }

        g2.setColor(bgColor);
        g2.fillRect(0, 0, width, height);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 40));
        String title = "LEVEL " + level + " BACKGROUND";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (width - titleWidth) / 2, height / 2 - 10);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
        String hint = "Replace /levels/level" + level + ".png with your own image";
        int hintWidth = g2.getFontMetrics().stringWidth(hint);
        g2.drawString(hint, (width - hintWidth) / 2, height / 2 + 30);

        g2.dispose();
        return image;
    }

    public void drawBackground(Graphics2D g2, int width, int height) {
        if (latarImg != null) {
            g2.drawImage(latarImg, 0, 0, width, height, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, width, height);
        }
    }

    public void setPintu(int x, int y) {
        pintuX = x;
        pintuY = y;
    }

    public int getPintuX() {
        return pintuX;
    }

    public int getPintuY() {
        return pintuY;
    }
}
