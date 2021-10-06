package com.eseo.finaspetit.agileproject.main.library;

import com.google.firebase.Timestamp;

public class Notification {
    private String message;
    private Timestamp dateCreation;
    private String receiver;

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

    public Notification(String m, Timestamp dateCreation, String receiver){
        this.dateCreation=dateCreation;
        this.receiver=receiver;
        this.message=m;
    }

    @Override
    public String toString() {
        return this.message +" ("+ this.dateCreation.toDate().toString()+")";
    }
}
