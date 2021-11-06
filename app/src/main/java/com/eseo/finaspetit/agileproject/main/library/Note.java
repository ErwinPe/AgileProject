package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

public class Note {
    private String note;
    private String user;

    public Note(String note, String u){
        this.note=note;
        this.user=u;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NonNull
    public String toString(){
        return this.user+ " : "+this.note;
    }



}
