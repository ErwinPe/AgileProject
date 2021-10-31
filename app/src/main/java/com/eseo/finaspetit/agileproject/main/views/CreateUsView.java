package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityCreateusBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class CreateUsView extends AppCompatActivity {

    private ActivityCreateusBinding binding;
    private Salon currentSaloon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateusBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Database ddb= new Database();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Création d'US : ");

        currentSaloon = ((Constants) CreateUsView.this.getApplication()).getCurentSaloon();

        binding.button3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Message> messages=new ArrayList<>();
                messages.add(new Message("US créée ","System", Timestamp.now()));
                US usToAdd=new US(binding.editTextNameUS.getText().toString(),binding.editTextDescUS.getText().toString(),null,messages,false,Timestamp.now(),"Created");
                ddb.addUSToSalon(usToAdd,currentSaloon.getId());
                System.out.println("CURRENT SALLON: "+currentSaloon.getId());
            }
        });
    }
}
