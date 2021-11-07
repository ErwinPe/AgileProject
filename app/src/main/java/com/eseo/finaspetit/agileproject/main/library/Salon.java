package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import java.util.ArrayList;

public class Salon {
    private String id;
    private Timestamp creationDate;
    private String description;
    private String nom;
    private ArrayList<Message> chat;
    private ArrayList<String> members;
    private ArrayList<US> US;
    private String scrumMaster;

    public Salon(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
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

    public ArrayList<Message> getChat() {
        return chat;
    }

    public void setChat(ArrayList<Message> chat) {
        this.chat = chat;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<com.eseo.finaspetit.agileproject.main.library.US> getUS() {
        return US;
    }

    public void setUS(ArrayList<com.eseo.finaspetit.agileproject.main.library.US> US) {
        this.US = US;
    }

    public String getScrumMaster() {
        return scrumMaster;
    }

    public void setScrumMaster(String scrumMaster) {
        this.scrumMaster = scrumMaster;
    }

    public Salon(String nom, String desc){
        this.nom=nom;
        System.out.println("Nom: "+nom);
        this.description=desc;
    }

    public Salon(String id, Timestamp date, String nom, String desc, ArrayList<Message> ch, ArrayList<String> mem, ArrayList<US> us){
        this.id=id;
        this.description=desc;
        this.creationDate=date;
        this.nom=nom;
        this.chat = ch;
        this.members = mem;
        this.US = us;
    }

    public Salon(String id, String nom, String desc, String scrumMaster,Timestamp date){
        this.id=id;
        this.description=desc;
        this.nom=nom;
        this.scrumMaster = scrumMaster;
        this.creationDate = date;
    }

    public Salon(String id, String nom, String desc, String scrumMaster,Timestamp date, ArrayList<String> m){
        this.id=id;
        this.description=desc;
        this.nom=nom;
        this.scrumMaster = scrumMaster;
        this.creationDate = date;
        this.members=m;
    }

    @NonNull
    public String toString(){
        return this.nom+" "+this.description+" "+this.creationDate+" "+this.chat+" "+this.members+" "+this.US;
    }
}
