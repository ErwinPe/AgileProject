package com.eseo.finaspetit.agileproject.main.library;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

public class Constants extends Application {
    public static final String SALON_COLLECTION= "salon";
    public static final String CHAT_COLLECTION= "CHAT";

    private Salon curentSaloon;

    public Salon getCurentSaloon() {
        return curentSaloon;
    }

    public void setCurentSaloon(Salon curentSaloon) {
        this.curentSaloon = curentSaloon;
    }

}
