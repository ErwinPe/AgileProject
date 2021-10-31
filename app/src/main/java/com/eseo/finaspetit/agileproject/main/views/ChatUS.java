package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatUsBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterMessage;
import com.eseo.finaspetit.agileproject.main.interfaces.ChatUSViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.US;

import java.util.ArrayList;
import java.util.List;


public class ChatUS extends AppCompatActivity implements ChatUSViewInterface {
    private ActivityChatUsBinding binding;
    private Database bdd=new Database();
    private String idSalon="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        idSalon =((Constants) ChatUS.this.getApplication()).getCurentSaloon().getId();
        US a=((Constants) ChatUS.this.getApplication()).getCurentUS();
        bdd.getAllMessagesFromUS(this,idSalon,a.getId()+"");
    }

    @Override
    public void handleMessageUS(List<Message> list) {
        CustomClassAdaptaterMessage adapt=new CustomClassAdaptaterMessage(this,list);
        binding.listView.setAdapter(adapt);
    }
}
