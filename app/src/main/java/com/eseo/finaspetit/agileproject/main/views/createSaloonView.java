package com.eseo.finaspetit.agileproject.main.views;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
//import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.eseo.finaspetit.agileproject.databinding.ActivityCreatesaloonBinding;
import com.eseo.finaspetit.agileproject.main.interfaces.CreateSaloonViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class createSaloonView  extends AppCompatActivity implements CreateSaloonViewInterface {
    private ActivityCreatesaloonBinding binding;
    private final Database db = new Database();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatesaloonBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        db.getAllUser(this);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Création d'un salon : ");

        binding.buttonCreateSaloon.setOnClickListener(v -> {
            ArrayList<String> listmembers = new ArrayList<>();
            listmembers.add(Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            Date date = new Date(System.currentTimeMillis());
            Timestamp currentTime = new Timestamp(date);
            Salon saloon = new Salon();
            saloon.setNom(binding.textSaloonName.getText().toString());
            saloon.setDescription(binding.textDescSaloon.getText().toString());
            saloon.setCreationDate(currentTime);
            saloon.setScrumMaster(auth.getCurrentUser().getEmail());
            String[] membersStr = binding.SearchMembers.getText().toString().split(", ");
            saloon.setMembers(listmembers);
            String idSaloon = db.createDocument(saloon, "salon");
            Message msg=new Message("Création du salon "+binding.textSaloonName.getText().toString() ,"System");
            db.addMessageToGeneralChat(idSaloon,msg);
            String message = " invitation au salon "+binding.textSaloonName.getText().toString();
            for (String s : membersStr) {
                if (!s.equals(" ") & !s.equals(auth.getCurrentUser().getEmail())) {
                    Notification notifCreateSaloon = new Notification(null,message, currentTime, s, "Invitation Salon",idSaloon);
                    db.createDocument(notifCreateSaloon, "notification");
                }
            }
            binding.textSaloonName.setText("");
            binding.textDescSaloon.setText("");
            binding.SearchMembers.setText("");
        });
    }

    public void handleGetAllUser(List<String> list){
        MultiAutoCompleteTextView textMembersSaloon =(MultiAutoCompleteTextView)binding.SearchMembers;
        ArrayAdapter adapterLanguages = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        textMembersSaloon.setAdapter(adapterLanguages);
        textMembersSaloon.setThreshold(1);
        textMembersSaloon.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

}
