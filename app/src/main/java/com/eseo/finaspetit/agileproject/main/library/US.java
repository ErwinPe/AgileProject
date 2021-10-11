package com.eseo.finaspetit.agileproject.main.library;

import com.google.firebase.Timestamp;
import java.util.ArrayList;

public class US {
    int id;
    Timestamp dateCreation;
    String description;
    String nom;
    ArrayList<Integer> notes = new ArrayList<>();
    ArrayList<Message> messages = new ArrayList<>();
    boolean isVoted;
    String etat;

    public US(String nom, String desc, ArrayList<Integer> notes, ArrayList<Message> messages, boolean isVoted, com.google.firebase.Timestamp dateCreation){
        this.nom=nom;
        this.description=desc;
        this.notes=notes;
        this.messages=messages;
        this.isVoted=isVoted;
        this.dateCreation=dateCreation;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public ArrayList<Integer> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Integer> notes) {
        this.notes = notes;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {
        isVoted = voted;
    }
}
