package org.model;

import org.entity.Pembeli;
import java.util.Map;

interface Jualable {
    void jual(Pembeli pembeli);
}



public class Menu implements Jualable {

    private String nama;
    private double hargaJual;
    private Map<String, Integer> resepBahan;
    private int levelRequired;

    public Menu(String nama, double hargaJual, Map<String, Integer> resepBahan, int levelRequired) {
        this.nama = nama;
        this.hargaJual = hargaJual;
        this.resepBahan = resepBahan;
        this.levelRequired = levelRequired;
    }

    public void siapkanMenu() {
        System.out.println("Menyiapkan " + nama);
    }

    @Override
    public void jual(org.entity.Pembeli pembeli) {
        System.out.println(nama + " dijual ke pembeli");
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public String getNama() {
        return nama;
    }
}