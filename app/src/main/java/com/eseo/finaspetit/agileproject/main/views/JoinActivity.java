package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityJoinBinding;

public class JoinActivity extends AppCompatActivity {

    private ActivityJoinBinding binding;
    private Spinner spinnerSaloon;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        this.spinnerSaloon = (Spinner) findViewById(binding.spinnerjoin.getId());

    }
}
