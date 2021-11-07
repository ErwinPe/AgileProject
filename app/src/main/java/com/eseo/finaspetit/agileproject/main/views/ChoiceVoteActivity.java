package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
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
        actionBar.setTitle(getResources().getString(R.string.actionBar_choice_vote_activity_title)+" "+currentUS.getNom());

        binding.userVote.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(binding.choiceNote.getSelectedItem().toString(), auth.getCurrentUser().getEmail() );
                bdd.addNoteToUS(note, currentUS.getId(),auth.getCurrentUser().getEmail());
                Toast.makeText(ChoiceVoteActivity.this,
                        getResources().getString(R.string.msg_vote_success),
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        final Spinner spinnerRegion = binding.choiceNote;
        List<String> note= new ArrayList<>();
        note.add(getResources().getString(R.string.vote_step_0));
        note.add(getResources().getString(R.string.vote_step_1));
        note.add(getResources().getString(R.string.vote_step_2));
        note.add(getResources().getString(R.string.vote_step_3));
        note.add(getResources().getString(R.string.vote_step_4));
        note.add(getResources().getString(R.string.vote_step_5));
        note.add(getResources().getString(R.string.vote_step_6));
        note.add(getResources().getString(R.string.vote_step_7));
        note.add(getResources().getString(R.string.vote_step_8));
        note.add(getResources().getString(R.string.vote_step_9));
        note.add(getResources().getString(R.string.vote_step_10));
        note.add(getResources().getString(R.string.vote_step_11));
        note.add(getResources().getString(R.string.vote_step_12));

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
