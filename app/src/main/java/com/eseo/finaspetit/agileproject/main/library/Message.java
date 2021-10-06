package com.eseo.finaspetit.agileproject.main.library;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.eseo.finaspetit.agileproject.main.views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Message {
    private String text;
    private String user;
    private Timestamp messageTime;

    public Message(String messageText, String messageUser) {
        this.text = messageText;
        this.user = messageUser;

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Timestamp currentTime = new Timestamp(date);
        this.messageTime = currentTime;
    }

    public Message(){

    }

    public String getMessageText() {
        return text;
    }

    public void setMessageText(String messageText) {
        this.text = messageText;
    }

    public String getMessageUser() {
        return user;
    }

    public void setMessageUser(String messageUser) {
        this.user = messageUser;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }
}
