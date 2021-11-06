package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class Message {
    private final String messageText;
    private final String messageUser;
    private final Timestamp messageTime;

    public Message(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        this.messageTime = new Timestamp(date);
    }

    public Message(String messageText,String messageUser,Timestamp tm){
        this.messageTime=tm;
        this.messageText=messageText;
        this.messageUser=messageUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    @NonNull
    @Override
    public String toString(){
        return this.messageText+" "+this.messageUser+" "+this.messageTime;
    }
}
