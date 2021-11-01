package com.eseo.finaspetit.agileproject.main.library;

import com.google.firebase.Timestamp;
import java.util.ArrayList;


public class US {
    String id;
    Timestamp dateCreation;
    String description;
    String nom;
    ArrayList<Note> notes;
    ArrayList<Message> messages = new ArrayList<>();
    boolean isVoted;
    String etat;
    String idSalon;

    public US(String id, String nom, String desc, ArrayList<Note> notes, ArrayList<Message> messages, boolean isVoted, com.google.firebase.Timestamp dateCreation, String etat, String idSalon){
        this.id=id;
        this.nom=nom;
        this.description=desc;
        this.notes=notes;
        this.messages=messages;
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

    public String getIdSalon() {
        return idSalon;
    }

    public void setIdSalon(String id) {
        this.idSalon = idSalon;
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

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
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

    @Override
    public String toString(){
        return this.nom+" "+this.dateCreation.toDate().toString()+" "+this.etat+" "+this.description+" "+this.notes+" "+this.idSalon;
    }
}
