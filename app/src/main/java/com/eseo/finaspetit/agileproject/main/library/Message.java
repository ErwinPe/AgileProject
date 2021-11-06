package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

public class Message {
    private String messageText;
    private String messageUser;
    private Timestamp messageTime;

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



    @NonNull
    @Override
    public String toString(){
        return this.messageText+" "+this.messageUser+" "+this.messageTime;
    }
}
