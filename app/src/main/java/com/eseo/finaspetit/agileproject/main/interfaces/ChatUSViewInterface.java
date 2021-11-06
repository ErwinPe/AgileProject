package com.eseo.finaspetit.agileproject.main.interfaces;

import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.US;

import java.util.List;

public interface ChatUSViewInterface {
    void handleMessageUS(List<Message> list);
    void gestBtnVote(US newUs);
}
