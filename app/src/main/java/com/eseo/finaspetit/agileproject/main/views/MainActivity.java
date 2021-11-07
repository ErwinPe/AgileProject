package com.eseo.finaspetit.agileproject.main.views;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.databinding.ActivityMainBinding;
import com.eseo.finaspetit.agileproject.main.interfaces.ReadAllMessagesInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ReadAllMessagesInterface {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    private final Database ddb=new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        auth = FirebaseAuth.getInstance();

        binding.buttonJoin.setEnabled(false);

        if(auth.getCurrentUser() == null) {
            ActivityResultLauncher<Intent> signinLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() != Activity.RESULT_OK) {
                                Toast.makeText(MainActivity.this, getResources().getString(R.string.msg_error_sign_in),
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig[]{
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build()
                    });
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setLogo(R.mipmap.ic_launcher)
                    .setAvailableProviders(providers).build();
            signinLauncher.launch(signInIntent);


        }else{

            ddb.addEmailInDatabaseIfUserInexistant(auth.getCurrentUser().getEmail());
            ddb.getAllSalon(this,auth.getCurrentUser().getEmail());
        }

        binding.buttonCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, createSaloonView.class);
            startActivity(intent);
        });

        binding.buttonJoin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.sign_out_menu){
            auth.signOut();
            finish();
            return true;
        }else if (item.getItemId()==R.id.usBouton){
            Intent intent = new Intent(this, NotificationView.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void handleResultAllSalon(List<Salon> content) {
        List<String> salonNames= new ArrayList<>();
        for(Salon s : content){
            if(Objects.requireNonNull(auth.getCurrentUser()).getEmail().equals(s.getScrumMaster())){
                salonNames.add("★  "+s.getNom()+"  ★");
            }else{
                salonNames.add(s.getNom());
            }

        }
        binding.buttonJoin.setEnabled(true);

        final Spinner spinnerRegion = binding.searchSaloon;
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, salonNames);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(dataAdapterR);

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                ((Constants) MainActivity.this.getApplication()).setCurentSaloon(content.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }



}