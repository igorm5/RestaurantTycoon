package org.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.ui.GamePanel;
import org.ui.RestaurantScene;

public class Pembeli extends Entity {
    GamePanel gp;
    private int targetX = 768/2;
    private int targetY = 576/2;
    private boolean isAktif;
    private String pesanan = "kopi";
    boolean sudahKeResepsionis = false;
    boolean sedangMenunggu = false;
    private int wait = 0;

    public Pembeli(GamePanel gp) {
        this.gp = gp;
        this.isAktif = true;

        setDefaultValues();
        getNPCImage();
    }

    public void setDefaultValues() {
        x = 0;
        y = 200;
        speed = 1;
        direction = "down";
    }

    public void getNPCImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(RestaurantScene restaurantScene) {

        if(!sudahKeResepsionis) {
            setTarget(restaurantScene.resepsionisX, restaurantScene.resepsionisY);

            if (x == targetX && y == targetY) {
                sedangMenunggu = true; 
            }

            if(sedangMenunggu){
                wait++;
                if(wait > 180) { // Menunggu selama 3/*  */ detik (180 frames)
                    sudahKeResepsionis = true;
                    sedangMenunggu = false;
                    wait = 0;

                    cekKursi(restaurantScene);
                }
            }
        }

        if (x < targetX) {
            direction = "right";
            x += speed;
        } else if (x > targetX) {
            direction = "left";
            x -= speed;
        }

        else if (y < targetY) {
            direction = "down";
            y += speed;
        } else if (y > targetY) {
            direction = "up";
            y -= speed;
        }

        spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        g2.setColor(Color.WHITE);
        g2.drawString(pesanan, x, y - 10);

    }

    public void setTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    public boolean isAktif() {
        return isAktif;
    }

    public void cekKursi(RestaurantScene kursi) {
        if(x == kursi.resepsionisX && y == kursi.resepsionisY) {
            for (RestaurantScene.Kursi k : kursi.daftarKursi) {
            if (k.isKosong) {
                setTarget(k.getX(), k.getY());
                k.isKosong = false;
                return;
            }else {
                // Jika semua kursi penuh, diberi logika lain (bisa langsung keluar atau tetap di tempat)
                setTarget(x, y); // Tetap di tempat
            }
        }
        }   
    }
}

