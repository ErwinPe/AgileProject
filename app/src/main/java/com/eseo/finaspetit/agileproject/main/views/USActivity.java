package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityUsBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterMessage;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterUS;
import com.eseo.finaspetit.agileproject.main.interfaces.UsViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.eseo.finaspetit.agileproject.main.library.US;

import java.util.ArrayList;

public class USActivity extends AppCompatActivity implements UsViewInterface {
    String idSalon="";
    ActivityUsBinding binding;
    Database bdd=new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        idSalon=((Constants) USActivity.this.getApplication()).getCurentSaloon().getId();
        bdd.getAllUS(this,idSalon);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = binding.listView.getItemAtPosition(position);
                US us = (US) o;
                Intent intent = new Intent(USActivity.this, ChatUS.class);
                ((Constants) USActivity.this.getApplication()).setCurentUS(us);
                startActivity(intent);
                //TODO: ALLER SUR LA PAGE DETAIL US
            }
        });
    }

    @Override
    public void handleUS(ArrayList<US> list) {
        CustomClassAdaptaterUS adapt=new CustomClassAdaptaterUS(this,list);
        binding.listView.setAdapter(adapt);
    }
}
