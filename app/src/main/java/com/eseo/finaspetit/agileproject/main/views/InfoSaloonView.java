package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityCreatesaloonBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityInfoSaloonBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityMainBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Salon;

public class InfoSaloonView extends AppCompatActivity {

    private ActivityInfoSaloonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoSaloonBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Informations du salon : ");

        Salon salon = ((Constants) InfoSaloonView.this.getApplication()).getCurentSaloon();

        binding.nameSalon.setText(salon.getNom());
        binding.descSalon.setText(salon.getDescription());
        binding.scrumMSalon.setText(salon.getScrumMaster());
        //binding.dateCreaSalon.setText(salon.getCreationDate().toString());


    }
}
