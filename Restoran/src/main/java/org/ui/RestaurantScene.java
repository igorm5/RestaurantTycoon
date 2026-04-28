package org.ui;

import java.util.ArrayList;

public class RestaurantScene {

    public class Kursi {
        private int x, y;
        public boolean isKosong;

        public Kursi(int x, int y) {
            this.x = x;
            this.y = y;
            this.isKosong = true;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public int resepsionisX = 100;
    public int resepsionisY = 200;

    // Di dalam class GamePanel:
    public ArrayList<Kursi> daftarKursi = new ArrayList<>();

    public void loadLevel(int level) {
        daftarKursi.clear(); // Hapus kursi level sebelumnya

        if (level == 1) {
            // Koordinat Kursi Level 1
            daftarKursi.add(new Kursi(200, 300));
            daftarKursi.add(new Kursi(400, 300));
        } else if (level == 2) {
            // Koordinat Kursi Level 2 (Kursi lebih banyak & posisi beda)
            daftarKursi.add(new Kursi(150, 200));
            daftarKursi.add(new Kursi(350, 200));
            daftarKursi.add(new Kursi(150, 450));
            daftarKursi.add(new Kursi(350, 450));
        }
        // ... dan seterusnya sampai Level 5
    }
}
