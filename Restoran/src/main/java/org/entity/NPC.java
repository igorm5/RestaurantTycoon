package org.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.ui.GamePanel;
import org.ui.RestaurantScene;

public class NPC extends Entity {
    GamePanel gp;
    private int targetX = 768/2;
    private int targetY = 576/2;
    private boolean isAktif;
    private String pesanan = "kopi";
    boolean sudahKeResepsionis = false;
    private int wait = 0;
    private boolean makan;

    public NPC(GamePanel gp) {
        this.gp = gp;
        this.isAktif = true;

        setDefaultValues();
        getNPCImage(gp.getGameManager().levelRestoran); // Default level
    }

    public void setDefaultValues() {
        x = 0;
        y = 200;
        speed = 1;
        direction = "down";
    }

    public void getNPCImage(int level) {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
            
            // logika duduk berdasarkan level (sementara hanya 2 level, bisa ditambah sampai 5)
            switch (level) {
            case 1:
                makanImg = ImageIO.read(getClass().getResourceAsStream("/player/duduk/kayukanan.png"));
                break;
            case 2:
                makanImg = ImageIO.read(getClass().getResourceAsStream("/player/duduk/kayukiri.png"));
                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            default:
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(RestaurantScene restaurantScene) {

        // 1. Logika pergerakan
        logikaGerak(restaurantScene);

        // 2. Logika Pergerakan
        boolean isMoving = false;

        if (x < targetX) {
            direction = "right";
            x += speed;
            isMoving = true;
        } else if (x > targetX) {
            direction = "left";
            x -= speed;
            isMoving = true;
        } else if (y < targetY) {
            direction = "down";
            y += speed;
            isMoving = true;
        } else if (y > targetY) {
            direction = "up";
            y -= speed;
            isMoving = true;
        }

        // 3. Animasi Sprite (Hanya berjalan jika isMoving true)
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            // Reset ke sprite default saat diam
            spriteNum = 1; 
        }
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        if(!makan){
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
        } else {
            image = makanImg; 
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

    public void keKursi(RestaurantScene kursi) {
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

    private boolean makanSelesai = false;
    public void logikaGerak(RestaurantScene logika) {

        // 1. Tentukan Target Berdasarkan Status
        if (!sudahKeResepsionis) {
            setTarget(logika.resepsionisX, logika.resepsionisY);
        } else if (!makanSelesai) { 
            // Selama belum selesai makan, tetap menuju/di kursi
            keKursi(logika); 
        } else {
            // Jika sudah ke resepsionis DAN sudah selesai makan
            keluar(logika); 
        }

        // 2. Logika Aksi Saat Sampai di Target
        if (x == targetX && y == targetY) {
            wait++;
            
            // AKSI DI RESEPSIONIS
            if (!sudahKeResepsionis) {
                direction = "up";
                if (wait > 180) { 
                    sudahKeResepsionis = true;
                    wait = 0; 
                }
            } 
            // AKSI SAAT MAKAN (Sudah di kursi)
            else if (!makanSelesai) {
                makan = true; // Ganti sprite ke makan
                if (wait > 600) { // Durasi makan selesai
                    makanSelesai = true;
                    makan = false; // Kembalikan sprite ke normal
                    // Bebaskan kursi
                    for (RestaurantScene.Kursi k : logika.daftarKursi) {
                        if (x == k.getX() && y == k.getY()) {
                            k.isKosong = true;
                            break; 
                        }
                    }
                    wait = 0; // Reset wait untuk perjalanan keluar
                }
            }
        } else {
            // Reset wait jika masih dalam perjalanan (belum sampai target)
            wait = 0; 
        }
    }

    public void keluar(RestaurantScene pintuKeluar) {
        setTarget(pintuKeluar.getPintuX(), pintuKeluar.getPintuY());
        if(x == targetX && y == targetY) {
            isAktif = false; // NPC tidak aktif lagi setelah keluar
        }
    }
}

