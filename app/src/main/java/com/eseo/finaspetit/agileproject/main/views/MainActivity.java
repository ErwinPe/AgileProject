package com.eseo.finaspetit.agileproject.main.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.eseo.finaspetit.agileproject.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("test");
    }
}