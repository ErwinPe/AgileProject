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

    public Salon(){

    }

    public String getId(){
        return this.id;
    }

    public String getNom(){
        return this.nom;
    }

    /*public Salon(String id){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection(Constants.SALON_COLLECTION).document(id);
        Task<DocumentSnapshot> snap=docRef.get();

        id=snap.getResult().get("ID").toString();
        System.out.println(this.id);
        //chat=
    }*/

    public Salon(Timestamp date, String nom, String desc, ArrayList<Message> ch, ArrayList<String> mem, ArrayList<US> us){
        this.description=desc;
        this.creationDate=date;
        this.nom=nom;
        this.chat = ch;
        this.members = mem;
        this.US = us;
    }

    public void createDocument(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        HashMap<String, Object> newSalon=new HashMap<>();
        newSalon.put("ID",this.id);
        newSalon.put("description",this.description);
        newSalon.put("nom",this.nom);
        newSalon.put("dateCreation",this.creationDate);

        ArrayList<Message> chat=new ArrayList<>();
        Message mes1=new Message("message1","Erwin");
       // Message mes2=new Message("message2","Quentin");

        chat.add(mes1);
        //chat.add(mes2);
        newSalon.put("CHAT",chat);

        ArrayList<US> US=new ArrayList<>();
        US us=new US("nom","desc");
        US.add(us);

        newSalon.put("US",US);

        firestore.collection("salon").add(newSalon);
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
                        //System.out.println(document.toString());
                        //Salon sal= new Salon(document.getTimestamp("dateCreation"),document.getString("nom"),document.getString("description"),(ArrayList<Message>) document.get("CHAT"),(ArrayList<String>) document.get("members"),(ArrayList<US>) document.get("US"));
                        Salon sal= new Salon(document.getTimestamp("dateCreation"),document.getString("nom"),document.getString("description"),null,null,null);
                        System.out.println("INSTANCIATION DE : "+sal.getNom());
                        System.out.println("non");
                        //System.out.println(sal.toString());
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
        return this.id+" "+this.nom+" "+this.description+" "+this.creationDate+" "+this.chat+" "+this.members+" "+this.US;
    }
}
