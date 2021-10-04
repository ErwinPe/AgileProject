package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());

        View root = binding.getRoot();
        setContentView(root);
        ddb.readAllMessages(this);
        ddb.getAllSalon(this);
    }


    public void handleResult(String contents){
        binding.textView.setText(contents);
    }

    public void handleResultAllSalon(List<Salon> content) {
        String a="";
        for(Salon s : content){
            a=a+s.getNom()+" ";
        }
        binding.textView3.setText(a);
    }


}
