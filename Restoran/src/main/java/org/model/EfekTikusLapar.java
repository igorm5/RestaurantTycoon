/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model;

/**
 *
 * @author ASUS
 */

import org.ui.GamePanel;

public class EfekTikusLapar extends Bencana {
    //ini ga kepake masihan

    public EfekTikusLapar() {
        super("Tikus Lapar");
    }

    @Override
    public void picuBencana(GamePanel gp) {
        System.out.println("🐀 Tikus makan bahan!");

        gp.gm.restaurant.kurangiStokRandom(2);
        gp.gm.kerugianHariIni += 20;
    }
}