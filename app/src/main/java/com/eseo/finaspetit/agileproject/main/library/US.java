package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class US {
    String id;
    Timestamp dateCreation;
    String description;
    String nom;
    boolean isVoted;
    String etat;
    String idSalon;

    public US(String id, String nom, String desc, boolean isVoted, com.google.firebase.Timestamp dateCreation, String etat, String idSalon){
        this.id=id;
        this.nom=nom;
        this.description=desc;
        this.isVoted=isVoted;
        this.dateCreation=dateCreation;
        this.etat=etat;
        this.idSalon=idSalon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    @NonNull
    @Override
    public String toString(){
        return this.nom+" "+this.dateCreation.toDate().toString()+" "+this.etat+" "+this.description+" "+this.idSalon;
    }
}
