package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.databinding.ActivityUsBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterUS;
import com.eseo.finaspetit.agileproject.main.interfaces.UsViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Note;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class USActivity extends AppCompatActivity implements UsViewInterface {
    String idSalon="";
    ActivityUsBinding binding;
    Database bdd=new Database();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUsBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);



        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("US : ");
        idSalon=((Constants) USActivity.this.getApplication()).getCurentSaloon().getId();
        bdd.getAllUS(this,idSalon);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = binding.listView.getItemAtPosition(position);
                US us = (US) o;
                System.out.println("ici ugetId: "+us);
                Intent intent = new Intent(USActivity.this, ChatUS.class);
                ((Constants) USActivity.this.getApplication()).setCurentUS(us);
                startActivity(intent);
            }
        });
    }

    @Override
    public void handleUS(ArrayList<US> list) {
        CustomClassAdaptaterUS adapt=new CustomClassAdaptaterUS(this,list);
        binding.listView.setAdapter(adapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_us,menu);
        if (!((Constants) USActivity.this.getApplication()).getCurentSaloon().getScrumMaster().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())) {

            menu.findItem(R.id.btnCreateUs).setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.btnCreateUs){
            Intent intent = new Intent(this, CreateUsView.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
