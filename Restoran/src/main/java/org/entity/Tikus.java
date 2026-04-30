package org.entity;


import org.model.Restoran;

abstract class Bencana {
    public abstract void picuBencana(Restoran r);
}

public class Tikus extends Entity {

    private Bencana bencana;

    public void picuBencana(Restoran r) {
        if (bencana != null) {
            bencana.picuBencana(r);
        }
    }

    public void setRencana(Bencana bencana) {
        this.bencana = bencana;
    }

    public void updatePosisi() {
        // contoh: jalan ke dapur
        x += speed;
    }
}
