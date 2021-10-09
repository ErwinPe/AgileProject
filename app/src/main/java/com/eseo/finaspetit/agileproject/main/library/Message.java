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
    private String messageText;
    private String messageUser;
    private Timestamp messageTime;

    public Message(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Timestamp currentTime = new Timestamp(date);
        this.messageTime = currentTime;
    }

    public Message(String messageText,String messageUser,Timestamp tm){
        this.messageTime=tm;
        this.messageText=messageText;
        this.messageUser=messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }

    public String toString(){
        return this.messageText+" "+this.messageUser+" "+this.messageTime;
    }
}
