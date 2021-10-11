package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityUsBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;

public class USActivity extends AppCompatActivity {
    String idSalon="OmhWTI9IhHQ5Xd4Ep5Cw";
    ActivityUsBinding binding;
    Database bdd=new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        //idSalon =((Constants) USActivity.this.getApplication()).getCurentSaloon().getId();
        bdd.getAllUS(this,idSalon);

    }
}
