package com.eseo.finaspetit.agileproject.main.library;

import android.app.Application;

public class Constants extends Application {
    private Salon curentSaloon;
    private US currentUS;

    public Salon getCurentSaloon() {
        return curentSaloon;
    }

    public void setCurentSaloon(Salon curentSaloon) {
        this.curentSaloon = curentSaloon;
    }

    public US getCurentUS() {
        return currentUS;
    }

    public void setCurentUS(US u) {
        this.currentUS = u;
    }

}
