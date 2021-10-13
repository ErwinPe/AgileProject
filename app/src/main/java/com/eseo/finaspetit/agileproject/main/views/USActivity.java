package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.databinding.ActivityUsBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterUS;
import com.eseo.finaspetit.agileproject.main.interfaces.UsViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class USActivity extends AppCompatActivity implements UsViewInterface {
    String idSalon="KByifjkaJmwIfSZzGOni";
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

        //idSalon =((Constants) USActivity.this.getApplication()).getCurentSaloon().getId();
        bdd.getAllUS(this,idSalon);
    }

    @Override
    public void handleUS(ArrayList<US> list) {
        CustomClassAdaptaterUS adapt=new CustomClassAdaptaterUS(this,list);
        binding.listView.setAdapter(adapt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        System.out.println("USSSSSS ");
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
