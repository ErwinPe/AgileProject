package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityCreatesaloonBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityInfoSaloonBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityMainBinding;

public class InfoSaloonView extends AppCompatActivity {

    private ActivityInfoSaloonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoSaloonBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);


    }
}
