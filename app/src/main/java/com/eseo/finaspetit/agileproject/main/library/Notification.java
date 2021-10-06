package com.eseo.finaspetit.agileproject.main.library;

import com.google.firebase.Timestamp;

public class Notification {
    private String id;
    private String message;
    private Timestamp dateCreation;
    private String receiver;
    private String title;
    private String idSalon;

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getIdSalon() { return idSalon; }

    public void setIdSalon(String idSalon) { this.idSalon = idSalon; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Notification(String id,String m, Timestamp dateCreation, String receiver, String title, String idSalon){
        this.dateCreation=dateCreation;
        this.receiver=receiver;
        this.message=m;
        this.title=title;
        this.idSalon=idSalon;
        this.id=id;
    }

    @Override
    public String toString() {
        return this.title +" ("+ this.message+")" + this.id;
    }
}
