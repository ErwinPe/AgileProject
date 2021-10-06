package com.eseo.finaspetit.agileproject.main.views;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
//import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityCreatesaloonBinding;
import com.eseo.finaspetit.agileproject.main.interfaces.CreateSaloonViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class createSaloonView  extends AppCompatActivity implements CreateSaloonViewInterface {
    private ActivityCreatesaloonBinding binding;
    private Database db = new Database();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MultiAutoCompleteTextView textMembersSaloon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatesaloonBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        db.getAllUser(this);



        binding.buttonCreateSaloon.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> listmembers = new ArrayList<>();
                listmembers.add(auth.getCurrentUser().getEmail());

                Date date = new Date(System.currentTimeMillis());
                Timestamp currentTime = new Timestamp(date);


                Salon saloon = new Salon();
                saloon.setNom(binding.textSaloonName.getText().toString());
                saloon.setDescription(binding.textDescSaloon.getText().toString());
                saloon.setCreationDate(currentTime);

                saloon.setScrumMaster(auth.getCurrentUser().getEmail());

                String[] membersStr = binding.SearchMembers.getText().toString().split(", ");
                saloon.setMembers(listmembers);

                for (String s : membersStr) {
                    if (!s.equals(" ")) {
                        listmembers.add(s);
                    }
                }

                db.createDocument(saloon,"salon");
            }
        });
    }

    public void handleGetAllUser(List<String> list){
        System.out.println("email USER " + list.toString());

        textMembersSaloon =(MultiAutoCompleteTextView)binding.SearchMembers;

        ArrayAdapter adapterLanguages = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);

        textMembersSaloon.setAdapter(adapterLanguages);

        textMembersSaloon.setThreshold(1);

        // The text separated by commas
        textMembersSaloon.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

}
