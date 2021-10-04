package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eseo.finaspetit.agileproject.databinding.ActivityNotificationBinding;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NotificationView extends AppCompatActivity implements ReadAllMessagesInterface{

    private ActivityNotificationBinding binding;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    Database ddb=new Database();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();
        setContentView(root);
        ddb.readAllMessages(this);
        ddb.getAllSalon(this,auth.getCurrentUser().getEmail());
    }


    public void handleResult(String contents){
        binding.textView.setText(contents);
    }

    public void handleResultAllSalon(List<Salon> content) {
        String a="";
        FirebaseAuth auth = FirebaseAuth.getInstance();
        for(Salon s : content){
            //if(s.getMembers().contains(auth.getCurrentUser().getEmail())){
                a=a+s.getNom()+"\n";
            //}

        }

        final Spinner spinnerRegion = binding.spinner;
        String[] lRegion={"France","USA"};
        ArrayAdapter<Salon> dataAdapterR = new ArrayAdapter<Salon>(this, android.R.layout.simple_spinner_item,content);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(dataAdapterR);
        //binding.textView3.setText(a);

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String myRegion = String.valueOf(spinnerRegion.getSelectedItem());
                Toast.makeText(NotificationView.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + myRegion,
                        Toast.LENGTH_SHORT).show(); }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }

        });


    }


}
