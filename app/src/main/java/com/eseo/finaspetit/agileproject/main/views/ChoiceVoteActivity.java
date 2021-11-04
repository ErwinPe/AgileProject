package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatUsBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityVoteBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.Timestamp;

public class ChoiceVoteActivity extends AppCompatActivity {

    private final Database bdd=new Database();
    US currentUS;
    private ActivityVoteBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Salon currentSaloon = ((Constants) ChoiceVoteActivity.this.getApplication()).getCurentSaloon();
        currentUS = ((Constants) ChoiceVoteActivity.this.getApplication()).getCurentUS();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Vote for the US : "+currentUS.getNom());

        binding.userVote.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoiceVoteActivity.this, ChatUS.class);
                startActivity(intent);
            }
        });
    }
}
