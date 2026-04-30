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

public abstract class Bencana {

    protected String nama;

    public Bencana(String nama) {
        this.nama = nama;
    }

    public abstract void picuBencana(GamePanel gp);

    public String getNama() {
        return nama;
    }
}