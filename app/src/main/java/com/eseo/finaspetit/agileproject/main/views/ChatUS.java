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
import com.eseo.finaspetit.agileproject.main.library.Note;
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
        actionBar.setTitle(getResources().getString(R.string.actionBar_chat_us_activity_title)+" "+currentUS.getNom());

        binding.button4.setOnClickListener(v -> {
            Message mes=new Message(binding.editTextTextPersonName.getText().toString(), Objects.requireNonNull(auth.getCurrentUser()).getEmail(), Timestamp.now());
            bdd.addMessageToUSChat(mes,currentUS.getId());
            binding.editTextTextPersonName.setText(R.string.empty_field);
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
        bdd.gestUs(this,currentUS.getId());
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.openVote){
            bdd.updateEtatUs(currentUS.getId(), getResources().getString(R.string.state_OPENVOTE));
            bdd.resetNoteFromUS(currentUS.getId());

        }else if(item.getItemId()==R.id.closeVote){
            bdd.updateEtatUs(currentUS.getId(), "CLOSEVOTE");
            bdd.getAllNoteFromUS(this, currentUS.getId(), "MANUEL");

        }else if(item.getItemId()==R.id.vote){
            Intent intent = new Intent(this, ChoiceVoteActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    //AJOUTER QUELQUE PART bdd.getResumeNotes()
    public void gestBtnVote(US newUs){


        currentUS.setEtat(newUs.getEtat());
        if(currentUS.getEtat().equals(getResources().getString(R.string.state_CREATED))) {
            btnCloseVote.setVisible(false);
            btnVote.setVisible(false);
            btnOpenVote.setVisible(true);
        }else if(currentUS.getEtat().equals(getResources().getString(R.string.state_OPENVOTE))){
            binding.button4.setEnabled(false);
            btnVote.setVisible(true);
            bdd.getAllNoteFromUS(this, currentUS.getId(), "AUTO");
            btnCloseVote.setVisible(true);
            btnOpenVote.setVisible(false);
        }else if(currentUS.getEtat().equals(getResources().getString(R.string.state_CLOSEVOTE))) {
            btnCloseVote.setVisible(false);
            btnVote.setVisible(false);
            btnOpenVote.setVisible(true);
        }else if(currentUS.getEtat().equals(getResources().getString(R.string.state_VOTED))) {
            btnCloseVote.setVisible(false);
            btnVote.setVisible(false);
            btnOpenVote.setVisible(false);
        }
        if (!((Constants) ChatUS.this.getApplication()).getCurentSaloon().getScrumMaster().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())) {
            btnOpenVote.setVisible(false);
            btnCloseVote.setVisible(false);
        }
    }

    public void gestBtnVoteByNote(List<Note> lNote, String etat){
        boolean found=true;
        String containMess = "";
        for (Note n : lNote){
            if(n.getUser().equals(auth.getCurrentUser().getEmail())){
                found=false;
            }

            if (n.getNote().equals("?")||n.getNote().equals("IMPOSSIBLE")){
                containMess += n.getUser()+ " n'a pas compris l'US, ";
            }else if (n.getNote().equals(getResources().getString(R.string.vote_step_12))){
                containMess += n.getUser()+ " a besoin d'une paus CAFE, ";
            }
        }
        btnVote.setVisible(found);
        int tailleMembers = currentSaloon.getMembers().size();
        int tailleNote =  lNote.size();
        if (etat.equals("MANUEL")){
            System.out.println("icisfsfsdf");
            bdd.updateEtatUs(currentUS.getId(), getResources().getString(R.string.state_CLOSEVOTE));
            currentUS.setEtat(getResources().getString(R.string.state_CLOSEVOTE));
            bdd.addNoteResumeToChatUS(currentUS.getId());
        }
        if (!containMess.equals("") && etat.equals("MANUEL")){
            Message mes = new Message(containMess,"System");
            bdd.addMessageToUSChat(mes,currentUS.getId());
        }else if (lNote.size()==currentSaloon.getMembers().size() && currentUS.getEtat().equals("CLOSEVOTE")){
            Note nMax = lNote.get(0);
            Note nMin = nMax;
            for (Note n : lNote){
                if (Integer.parseInt(n.getNote())<Integer.parseInt(nMin.getNote())){
                    nMin=n;
                }else if (Integer.parseInt(n.getNote())>Integer.parseInt(nMax.getNote())){
                    nMax=n;
                }
            }
            System.out.println("USER MAX"+nMax.getUser()+" note "+nMax.getNote());
            System.out.println("USER MIN"+nMin.getUser()+" note "+nMin.getNote());

            if (nMin.getNote()==nMax.getNote()){
                bdd.updateEtatUs(currentUS.getId(), getResources().getString(R.string.state_VOTED));
                currentUS.setEtat(getResources().getString(R.string.state_VOTED));
            }else if (auth.getCurrentUser().getEmail().equals(nMin.getUser()) || auth.getCurrentUser().getEmail().equals(nMax.getUser())){
                System.out.println("je suis la ");
                binding.button4.setEnabled(true);
            }
        }


    }
}
