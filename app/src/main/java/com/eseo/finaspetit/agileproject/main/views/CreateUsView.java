package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.eseo.finaspetit.agileproject.databinding.ActivityCreateusBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.Timestamp;


public class CreateUsView extends AppCompatActivity {

    private ActivityCreateusBinding binding;
    private Salon currentSaloon = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateusBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Database ddb= new Database();
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Création d'US : ");

        currentSaloon = ((Constants) CreateUsView.this.getApplication()).getCurentSaloon();

        binding.button3.setOnClickListener(v -> {
            if(binding.editTextDescUS.getText().toString().length() != 0 && binding.editTextNameUS.getText().toString().length() != 0){
                US usToAdd=new US("",binding.editTextNameUS.getText().toString(),binding.editTextDescUS.getText().toString(),false,Timestamp.now(),"CREATED",currentSaloon.getId());
                ddb.addUSToSalon(usToAdd);
                Toast.makeText(CreateUsView.this,
                        "US créée ! ",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(CreateUsView.this,
                        "Aucun champ ne doit être vide ! ",
                        Toast.LENGTH_SHORT).show();
                
            }

        });
    }
}
