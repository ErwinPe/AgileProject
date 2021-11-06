package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
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
import java.util.Objects;


public class ChatUS extends AppCompatActivity implements ChatUSViewInterface {
    private ActivityChatUsBinding binding;
    private final Database bdd=new Database();
    private US currentUS=null;
    Salon currentSaloon;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    MenuItem btnCloseVote;
    MenuItem btnOpenVote;
    MenuItem btnVote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        currentSaloon = ((Constants) ChatUS.this.getApplication()).getCurentSaloon();
        currentUS =((Constants) ChatUS.this.getApplication()).getCurentUS();
        bdd.getAllMessagesFromUS(this,currentSaloon.getId(),currentUS.getId());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("US : "+currentUS.getNom());



        binding.button4.setOnClickListener(v -> {
            Message mes=new Message(binding.editTextTextPersonName.getText().toString(), Objects.requireNonNull(auth.getCurrentUser()).getEmail(), Timestamp.now());
            bdd.addMessageToUSChat(mes,currentUS.getId());
            binding.editTextTextPersonName.setText("");
        });
    }

    @Override
    public void handleMessageUS(List<Message> list) {
        CustomClassAdaptaterMessage adapt=new CustomClassAdaptaterMessage(this,list);
        binding.listView.setAdapter(adapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_chat_us,menu);
        btnCloseVote = menu.findItem(R.id.closeVote);
        btnOpenVote =  menu.findItem(R.id.openVote);
        btnVote = menu.findItem(R.id.vote);
        bdd.gestUs(this,currentSaloon.getId(),currentUS.getId());
        /*if (!((Constants) ChatUS.this.getApplication()).getCurentSaloon().getScrumMaster().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())) {
            btnOpenVote.setVisible(false);

        }
        btnCloseVote.setVisible(false);
        btnVote.setVisible(false);*/
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.openVote){
            bdd.updateEtatUs(currentUS.getId(), "OPENVOTE");
        }else if(item.getItemId()==R.id.closeVote){
            bdd.updateEtatUs(currentUS.getId(), "CLOSEVOTE");
        }else if(item.getItemId()==R.id.vote){
            Intent intent = new Intent(this, ChoiceVoteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void gestBtnVote(US newUs){
        currentUS.setEtat(newUs.getEtat());
        if(currentUS.getEtat().equals("CREATED")) {
            btnCloseVote.setVisible(false);
            btnVote.setVisible(false);
            btnOpenVote.setVisible(true);
        }else if(currentUS.getEtat().equals("OPENVOTE")){
            btnCloseVote.setVisible(true);
            btnVote.setVisible(true);
            btnOpenVote.setVisible(false);
        }else if(currentUS.getEtat().equals("CLOSEVOTE")) {
            btnCloseVote.setVisible(false);
            btnVote.setVisible(false);
            btnOpenVote.setVisible(true);
        }else if(currentUS.getEtat().equals("VOTED")) {
            btnCloseVote.setVisible(false);
            btnVote.setVisible(false);
            btnOpenVote.setVisible(false);
        }
        if (!((Constants) ChatUS.this.getApplication()).getCurentSaloon().getScrumMaster().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())) {
            btnOpenVote.setVisible(false);
            btnCloseVote.setVisible(false);
        }
    }
}
