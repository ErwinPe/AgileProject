package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Database {


    public String getListItems() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("notification").document("notif1");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        System.out.println(document.getString("message"));
                    } else {
                        System.out.println("Aucune donnée");
                    }
                } else {
                    System.out.println("erreur "+task.getException());
                }
            }
        });
        return "a modifier";
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
}
