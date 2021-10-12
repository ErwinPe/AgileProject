package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatUsBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.US;


public class ChatUS extends AppCompatActivity {
    private ActivityChatUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        //US firstKeyName = (US) getIntent().getSerializableExtra("actualUS");
        US a=((Constants) ChatUS.this.getApplication()).getCurentUS();
        System.out.println(a.toString());
        binding.textView6.setText("ok");
    }

}
