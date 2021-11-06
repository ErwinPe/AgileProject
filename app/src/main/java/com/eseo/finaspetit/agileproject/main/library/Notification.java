package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class Notification {
    private String id;
    private String message;
    private final Timestamp dateCreation;
    private final String receiver;
    private final String title;
    private final String idSalon;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getIdSalon() { return idSalon; }

    public String getTitle() { return title; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Notification(String id,String m, Timestamp dateCreation, String receiver, String title, String idSalon){
        this.dateCreation=dateCreation;
        this.receiver=receiver;
        this.message=m;
        this.title=title;
        this.idSalon=idSalon;
        this.id=id;
    }

    @NonNull
    @Override
    public String toString() {
        return this.title +" ("+ this.message+")" + this.id + " "+this.dateCreation+ " "+this.receiver;
    }
}
