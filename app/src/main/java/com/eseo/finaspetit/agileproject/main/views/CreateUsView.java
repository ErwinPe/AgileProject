package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityCreateusBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Salon;

public class CreateUsView extends AppCompatActivity {

    private ActivityCreateusBinding binding;
    private Salon currentSaloon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateusBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Création d'US : ");

        currentSaloon = ((Constants) CreateUsView.this.getApplication()).getCurentSaloon();
    }
}
