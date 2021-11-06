package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.databinding.ActivityChatUsBinding;
import com.eseo.finaspetit.agileproject.databinding.ActivityVoteBinding;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.eseo.finaspetit.agileproject.main.library.Note;
import com.eseo.finaspetit.agileproject.main.library.Salon;
import com.eseo.finaspetit.agileproject.main.library.US;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChoiceVoteActivity extends AppCompatActivity {

    private final Database bdd=new Database();
    US currentUS;
    private ActivityVoteBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityVoteBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        Salon currentSaloon = ((Constants) ChoiceVoteActivity.this.getApplication()).getCurentSaloon();
        currentUS = ((Constants) ChoiceVoteActivity.this.getApplication()).getCurentUS();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Vote for the US : "+currentUS.getNom());

        binding.userVote.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(binding.choiceNote.getSelectedItem().toString(), auth.getCurrentUser().getEmail() );
                bdd.addNoteToUS(note, currentUS.getId());
                Intent intent = new Intent(ChoiceVoteActivity.this, ChatUS.class);
                startActivity(intent);
            }
        });

        final Spinner spinnerRegion = binding.choiceNote;
        List<String> note= new ArrayList<>();
        note.add("?");
        note.add("0");
        note.add("1");
        note.add("2");
        note.add("3");
        note.add("5");
        note.add("8");
        note.add("13");
        note.add("20");
        note.add("40");
        note.add("100");
        note.add("IMPOSSIBLE");
        note.add("COFFE");

        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, note);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRegion.setAdapter(dataAdapterR);

        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
