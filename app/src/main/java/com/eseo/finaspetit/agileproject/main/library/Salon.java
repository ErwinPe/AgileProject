package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.eseo.finaspetit.agileproject.main.library.Constants;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.model.Document;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public ArrayList<Message> getChat() {
        return chat;
    }

    public void setChat(ArrayList<Message> chat) {
        this.chat = chat;
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

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
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

    public Salon(String nom,String desc){
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



    public Salon getDocument(String id){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef=firestore.collection("salon").document(id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        //Salon sal= new Salon(document.getTimestamp("dateCreation"),document.getString("nom"),document.getString("description"),(ArrayList<Message>) document.get("CHAT"),(ArrayList<String>) document.get("members"),(ArrayList<US>) document.get("US"));
                        //Salon sal= new Salon(document.getTimestamp("dateCreation"),document.getString("nom"),document.getString("description"),null,null,null);
                        Salon sal=new Salon(document.getString("nom"),document.getString("description"));
                        System.out.println("INSTANCIATION DE : "+sal.getNom());
                    } else {
                        System.out.println("Aucune donnée");
                    }
                } else {
                    System.out.println("erreur "+task.getException());
                }
            }
        });



        return null;
    }

    public String toString(){
        return this.nom+" "+this.description+" "+this.creationDate+" "+this.chat+" "+this.members+" "+this.US;
    }
}
