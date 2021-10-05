package com.eseo.finaspetit.agileproject.main.views;

import com.eseo.finaspetit.agileproject.main.library.Salon;

import java.util.List;

public interface ReadAllMessagesInterface {

    public void handleResult(String contents);
    public void handleResultAllSalon(List<Salon> content);
}
