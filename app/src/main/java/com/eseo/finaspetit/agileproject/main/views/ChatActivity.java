package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityNotificationBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptater;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterMessage;
import com.eseo.finaspetit.agileproject.main.interfaces.ChatViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatViewInterface {
    ActivityChatBinding binding;
    Database bdd=new Database();
    //TODO: Récupérer l'id du salon concerné
    String idSalon="737YkPrNN7rf7sJLsEbj";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        bdd.getAllMessages(this,idSalon);
        scrollListViewToBottom();
        binding.btnEnvoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg=new Message(binding.textInput.getText().toString(),auth.getCurrentUser().getEmail());
                System.out.println("DATA MESSAGE: "+msg.getMessageText());
                if(!msg.getMessageText().isEmpty()){
                    bdd.addMessageToGeneralChat(idSalon,msg);
                    binding.textInput.setText("");
                }

            }
        }
        );
    }

    @Override
    public void handleMessage(List<Message> list) {
        CustomClassAdaptaterMessage adapt=new CustomClassAdaptaterMessage(this,list);
        binding.listView.setAdapter(adapt);

    }

    private void scrollListViewToBottom() {

        binding.listView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                binding.listView.setSelection(binding.listView.getCount());
            }
        });
    }
}
