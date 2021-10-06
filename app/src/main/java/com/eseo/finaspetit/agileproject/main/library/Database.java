package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.main.interfaces.NotificationsViewsInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.ReadAllMessagesInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Database {

    public Database(){

    }

    public String getupdate(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String data="";
        final DocumentReference docRef = firestore.collection("notification").document("notif1");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println("Listen failed");
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    System.out.println("Current data "+snapshot.getData());
                } else {
                    System.out.println("Current data : null");
                }
            }
        });
        return data;
    }

    public void readAllMessages(AppCompatActivity act) {//Context context
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document("qu5rbrhQRw10FTvfAIpw").collection("CHAT").document("message1");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        ((ReadAllMessagesInterface)act).handleResult(document.getString("message"));
                    } else {
                        System.out.println("Aucune donnée");
                    }
                } else {
                    System.out.println("erreur "+task.getException());
                }
            }
        });

    }

    public void getAllSalon(AppCompatActivity act,String email) {//Context context
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        firestore.collection("salon").whereArrayContains("members",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Salon> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Salon sal=new Salon(document.getString("nom"),document.getString("description"));
                        list.add(sal);
                        ((ReadAllMessagesInterface)act).handleResultAllSalon(list);
                    }
                } else {
                    System.out.println("error : "+task.getException());
                }
            }
        });

    }

    //OFFICIEL
    public void createDocument(Object obj, String collection){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(collection).add(obj);
    }

    public void addMessageToGeneralChat(String idSalon,Message message){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("salon").document(idSalon);
        washingtonRef.update("chat", FieldValue.arrayUnion(message));
    }

    public void addMessageToUSChat(String idSalon,Message message, String idUS){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("salon").document(idSalon);

        //washingtonRef.update("messages", FieldValue.arrayUnion(message));

    }

    public ArrayList<Salon> getAllSalons() throws ExecutionException, InterruptedException {
        ArrayList<Salon> salons=new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document("qu5rbrhQRw10FTvfAIpw").collection("CHAT").document("message1");

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("chats")
                .limitToLast(50);
        return salons;
    }

    public void getNotification(AppCompatActivity act,String email){
        ArrayList<Notification> notif=new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        //DocumentReference docRef = firestore.collection("notification").document("notif1"); //document("qu5rbrhQRw10FTvfAIpw");


        firestore.collection("notification").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Notification> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Notification notif=new Notification(document.getString("message"),document.getTimestamp("dateCreation"),document.getString("receiver"));
                        list.add(notif);
                        ((NotificationsViewsInterface)act).handleNotification(list);
                    }
                } else {
                    System.out.println("error : "+task.getException());
                }
            }
        });
    }




}
