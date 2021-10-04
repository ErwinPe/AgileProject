package com.eseo.finaspetit.agileproject.main.library;

import java.sql.Timestamp;
import java.util.ArrayList;

public class US {
    int id;
    Timestamp dateCreation;
    String description;
    String nom;
    ArrayList<Integer> notes = new ArrayList<>();
    ArrayList<Message> messages = new ArrayList<>();
    boolean isVoted;

    public US(String nom,String desc){
        this.nom=nom;
        this.description=desc;

    }

    public String getNom(){
        return this.nom;
    }
    public String getDescription(){
        return this.description;
    }
}
