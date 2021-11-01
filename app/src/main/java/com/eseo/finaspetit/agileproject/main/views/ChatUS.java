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
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;


public class ChatUS extends AppCompatActivity implements ChatUSViewInterface {
    private ActivityChatUsBinding binding;
    private final Database bdd=new Database();
    private US currentUS=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Salon currentSaloon = ((Constants) ChatUS.this.getApplication()).getCurentSaloon();
        currentUS =((Constants) ChatUS.this.getApplication()).getCurentUS();
        bdd.getAllMessagesFromUS(this,currentSaloon.getId(),currentUS.getId());
        FirebaseAuth auth=FirebaseAuth.getInstance();

        binding.button4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message mes=new Message(binding.editTextTextPersonName.getText().toString(),auth.getCurrentUser().getEmail(), Timestamp.now());
                bdd.addMessageToUSChat(mes,currentUS.getId());
            }
        });
    }

    @Override
    public void handleMessageUS(List<Message> list) {
        CustomClassAdaptaterMessage adapt=new CustomClassAdaptaterMessage(this,list);
        binding.listView.setAdapter(adapt);
    }
}
