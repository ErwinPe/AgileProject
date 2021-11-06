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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(String idSalon) {
        this.idSalon = idSalon;
    }

    public US(String id, String nom, String desc, boolean isVoted, com.google.firebase.Timestamp dateCreation, String etat, String idSalon){
        this.id=id;
        this.nom=nom;
        this.description=desc;
        this.isVoted=isVoted;
        this.dateCreation=dateCreation;
        this.etat=etat;
        this.idSalon=idSalon;
    }

    @NonNull
    @Override
    public String toString(){
        return this.nom+" "+this.dateCreation.toDate().toString()+" "+this.etat+" "+this.description+" "+this.idSalon;
    }
}
