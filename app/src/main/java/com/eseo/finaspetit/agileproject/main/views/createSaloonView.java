package com.eseo.finaspetit.agileproject.main.views;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityCreatesaloonBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityMainBinding;

public class createSaloonView  extends AppCompatActivity {
    private ActivityCreatesaloonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatesaloonBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        binding.buttonAddMembers.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.autoTextMembers.getCompletionHint();
            }
        });

        binding.buttonCreateSaloon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.textSaloonName.getText().toString();
                String desc = binding.textDescSaloon.getText().toString();
                String members = binding.autoTextMembers.getText().toString();

            }
        });
    }

}
