package org.main;

import java.util.Random;

import org.ui.GamePanel;
import org.ui.RestaurantScene;
import org.model.Restoran;

public class GameManager {
    private GamePanel gp;
    public RestaurantScene restaurantScene;
    private Random random = new Random();
    private int[] jadwalBencana;
    private int totalBencanaHariIni;
    public Restoran restaurant = new Restoran();
    public int kerugianHariIni = 0;

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

        gp.detikBerjalan = 0; 

        siapkanBencana();
        restaurantScene.loadLevel(levelRestoran);
        siapkanJadwalNPC();
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
        
        for (int i = 0; i < jadwalBencana.length; i++) {
            if (jadwalBencana[i] == detikBerjalan) {

                triggerBencana();

                jadwalBencana[i] = -1; // biar gak ke-trigger lagi
            }
        }
    }

    public void akhirHari() {
        sedangBerjualan = false;
        // Panggil fungsi rekap dan simpan data
        System.out.println("Hari berakhir. Menampilkan Rekap...");
    }
    
    public void triggerBencana() {

        if (Math.random() <= 1.0) {
            gp.showTikusWarning();
            gp.startShake(30);
            
            gp.gm.restaurant.kurangiStokRandom(2);
            gp.gm.kerugianHariIni += 20;
            
            //nanti dipisah jadi class sendiri implements interface bencana
            System.out.println("Tikus datanggggg");
        } else {
            System.out.println("💨 Pembeli kabur!");
        }
    }
    
    public void siapkanBencana() {

        totalBencanaHariIni = 1 + random.nextInt(2);

        jadwalBencana = new int[totalBencanaHariIni];

        for (int i = 0; i < totalBencanaHariIni; i++) {
            // muncul antara detik 60 - 240 (biar gak awal/akhir banget)
            jadwalBencana[i] = 60 + random.nextInt(180);
        }
    }
    
}