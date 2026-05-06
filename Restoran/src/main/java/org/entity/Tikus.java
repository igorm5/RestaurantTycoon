package org.entity;

import java.awt.Graphics2D;

import org.model.EfekTikusLapar;
import org.ui.GamePanel;
import org.model.Bencana;
import java.awt.Color;

public class Tikus extends Entity {
    //ini gjadi kepake

    GamePanel gp;
    private boolean sudahEfek = false;
    private Bencana efek;

    public Tikus(GamePanel gp, Bencana efek) {
        this.gp = gp;
        this.efek = efek;

        x = 0;
        y = 400;
        speed = 2;
        direction = "right";
        
        System.out.println("Tikus dibuat di posisi: " + x + "," + y);
    }

    public void update() {

        x += speed;

        if (!sudahEfek && x > 300) {
            efek.picuBencana(gp);
            sudahEfek = true;
        }

//        if (x > gp.screenWidth) {
//            gp.listTikus.remove(this);
//        }
    }

//    public void draw(Graphics2D g2) {
//        g2.drawString("🐀", x, y);
//    }
    
    public void draw(Graphics2D g2) {
        g2.setColor(Color.RED);
        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}