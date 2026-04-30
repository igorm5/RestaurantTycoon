/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.entity;

import java.util.ArrayList;
import java.util.List;

import org.model.Menu;

public class Pembeli extends Entity {

    private List<Menu> pesanan = new ArrayList<>();
    private boolean isDuduk = false;

    public void generatePesanan(List<Menu> menuTersedia) {
        if (menuTersedia.isEmpty()) return;

        pesanan.add(menuTersedia.get(0)); // simple logic dulu
    }

    public void updatePosisi() {
        // Bisa kamu isi logic jalan ke kursi
    }

    public List<Menu> getPesanan() {
        return pesanan;
    }

    public void setDuduk(boolean duduk) {
        this.isDuduk = duduk;
    }
}