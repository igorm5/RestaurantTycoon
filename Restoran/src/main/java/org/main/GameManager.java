package org.main;

import java.util.Random;

import org.ui.GamePanel;
import org.ui.RestaurantScene;

public class GameManager {
    private GamePanel gp;
    public RestaurantScene restaurantScene;
    private Random random = new Random();

    // Data Progres Game
    public int hariKe = 1;
    public int levelRestoran = 1;
    public boolean sedangBerjualan = false;

    // Data NPC Hari Ini
    public int totalNPCHariIni;
    public int[] jadwalKedatangan;

    public GameManager(GamePanel gp) {
        this.gp = gp;
        this.restaurantScene = new RestaurantScene();
    }

    // Dipanggil saat tombol "Buka Restoran" diklik
    public void mulaiFasePenjualan() {
        sedangBerjualan = true;

        // loadlevel
        restaurantScene.loadLevel(levelRestoran);

        siapkanJadwalNPC();
        gp.startGameThread(); // Pastikan loop di GamePanel jalan
    }

    private void siapkanJadwalNPC() {
        // Logika: Level tinggi = pelanggan lebih banyak 
        totalNPCHariIni = (levelRestoran * 5) + random.nextInt(10);
        jadwalKedatangan = new int[totalNPCHariIni];

        for (int i = 0; i < totalNPCHariIni; i++) {
            // Pelanggan datang acak dalam rentang 300 detik (5 menit)
            jadwalKedatangan[i] = random.nextInt(300);
        }
    }

    public void updateWaktu(int detikBerjalan) {
        if (!sedangBerjualan) return;

        // Cek apakah ada NPC yang harus muncul di detik ini
        for (int i = 0; i < jadwalKedatangan.length; i++) {
            if (jadwalKedatangan[i] == detikBerjalan) {
                gp.spawnNPC(); 
                jadwalKedatangan[i] = -1; // Tandai agar tidak muncul lagi
            }
        }

        // Cek jika waktu habis (300 detik)
        if (detikBerjalan >= 300) {
            akhirHari();
        }
    }

    public void akhirHari() {
        sedangBerjualan = false;
        // Panggil fungsi rekap dan simpan data
        System.out.println("Hari berakhir. Menampilkan Rekap...");
    }
}