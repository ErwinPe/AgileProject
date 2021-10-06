package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityNotificationBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptater;
import com.eseo.finaspetit.agileproject.main.interfaces.NotificationsViewsInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.ReadAllMessagesInterface;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class NotificationView extends AppCompatActivity implements ReadAllMessagesInterface, NotificationsViewsInterface {

    private ActivityNotificationBinding binding;
    Database ddb=new Database();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();
        setContentView(root);
        ddb.readAllMessages(this);
        ddb.getAllSalon(this,auth.getCurrentUser().getEmail());
        ddb.getNotification(this,auth.getCurrentUser().getEmail());
        binding.button2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println(binding.spinner.getSelectedItem());
            }
        });
    }


    public void handleResult(String contents){
        binding.textView.setText(contents);
    }

    public void handleResultAllSalon(List<Salon> content) {
        List<String> salonNames= new ArrayList<>();
        for(Salon s : content){
                salonNames.add(s.getNom());
        }

        final Spinner spinnerRegion = binding.spinner;
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,salonNames);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(dataAdapterR);

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

            }

        });


    }


    @Override
    public void handleNotification(List<Notification> list) {

        /*ArrayAdapter<Notification> arrayAdapter
                = new ArrayAdapter<Notification>(this, android.R.layout.simple_list_item_checked , list);*/

        binding.listView.setAdapter(new CustomClassAdaptater(this,list));

    }
}
