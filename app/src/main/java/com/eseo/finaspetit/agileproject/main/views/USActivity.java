package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityUsBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterMessage;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterUS;
import com.eseo.finaspetit.agileproject.main.interfaces.UsViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.US;

import java.util.ArrayList;

public class USActivity extends AppCompatActivity implements UsViewInterface {
    String idSalon="KByifjkaJmwIfSZzGOni";
    ActivityUsBinding binding;
    Database bdd=new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("US : ");

        //idSalon =((Constants) USActivity.this.getApplication()).getCurentSaloon().getId();
        bdd.getAllUS(this,idSalon);
    }

    @Override
    public void handleUS(ArrayList<US> list) {
        //System.out.println("ici "+list.get(0).getNotes());
        CustomClassAdaptaterUS adapt=new CustomClassAdaptaterUS(this,list);
        binding.listView.setAdapter(adapt);
    }
}
