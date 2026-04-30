/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.model;

import java.util.ArrayList;
import java.util.List;

public class Restoran {

    private int level = 1;
    private double uang = 0;
    private int kapasitasMaks = 2;

    private List<Menu> daftarMenuTerbuka = new ArrayList<>();

    public void upgradeLevel() {
        level++;
        kapasitasMaks += 2;
        System.out.println("Restoran naik ke level " + level);
    }

    public int getKapasitasBerdasarkanLevel() {
        return kapasitasMaks;
    }

    public int getJumlahMenuBerdasarkanLevel() {
        return daftarMenuTerbuka.size();
    }

    public void tambahMenu(Menu menu) {
        daftarMenuTerbuka.add(menu);
    }

    public List<Menu> getDaftarMenu() {
        return daftarMenuTerbuka;
    }

    public void tambahUang(double jumlah) {
        uang += jumlah;
    }

    public double getUang() {
        return uang;
    }

    public int getLevel() {
        return level;
    }
}