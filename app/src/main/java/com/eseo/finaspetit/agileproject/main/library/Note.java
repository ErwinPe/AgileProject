package com.eseo.finaspetit.agileproject.main.library;

import androidx.annotation.NonNull;

public class Note {
    private int note;
    private String user;

    public Note(int note, String u){
        this.note=note;
        this.user=u;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
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
