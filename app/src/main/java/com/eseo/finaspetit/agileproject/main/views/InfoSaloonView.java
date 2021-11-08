package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.databinding.ActivityInfoSaloonBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Salon;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.Locale;


public class InfoSaloonView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInfoSaloonBinding binding;
        binding = ActivityInfoSaloonBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(getResources().getString(R.string.actionBar_Info_Saloon_activity_title));

        Salon salon = ((Constants) InfoSaloonView.this.getApplication()).getCurentSaloon();

        binding.nameSalon.setText(salon.getNom());
        binding.descSalon.setText(salon.getDescription());
        binding.scrumMSalon.setText(salon.getScrumMaster());
        String formattedDate = new SimpleDateFormat(getResources().getString(R.string.pattern_timestamp), Locale.FRANCE).format(salon.getCreationDate().toDate());
        binding.dateSalon.setText(formattedDate);
        String listMembre = "";
        for (String m : salon.getMembers()){
            listMembre += m +", ";
        }
        binding.memberSalon.setText(listMembre);

    }
}
