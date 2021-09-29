package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.eseo.finaspetit.agileproject.databinding.ActivityNotificationBinding;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.*;

public class NotificationView extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    Database ddb=new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        binding.textView.setText(ddb.getListItems());
        ddb.getupdate();
    }





}
