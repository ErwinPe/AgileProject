package com.eseo.finaspetit.agileproject.main.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.databinding.ActivityChatBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptaterMessage;
import com.eseo.finaspetit.agileproject.main.interfaces.ChatViewInterface;
import com.eseo.finaspetit.agileproject.main.library.Constants;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity implements ChatViewInterface {
    ActivityChatBinding binding;
    Database bdd=new Database();
    String idSalon="";
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        idSalon =((Constants) ChatActivity.this.getApplication()).getCurentSaloon().getId();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Salon : "+((Constants) ChatActivity.this.getApplication()).getCurentSaloon().getNom());
        bdd.getAllMessages(this,idSalon);
        scrollListViewToBottom();
        binding.btnEnvoi.setOnClickListener(v -> {
            Message msg=new Message(Objects.requireNonNull(binding.textInput.getText()).toString(), Objects.requireNonNull(auth.getCurrentUser()).getEmail());
            if(!msg.getMessageText().isEmpty()){
                bdd.addMessageToGeneralChat(idSalon,msg);
                binding.textInput.setText("");
            }
        });
    }

    @Override
    public void handleMessage(List<Message> list) {
        CustomClassAdaptaterMessage adapt=new CustomClassAdaptaterMessage(this,list);
        binding.listView.setAdapter(adapt);
    }

    private void scrollListViewToBottom() {
        binding.listView.post(() -> binding.listView.setSelection(binding.listView.getCount()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main_saloon,menu);
        if (!((Constants) ChatActivity.this.getApplication()).getCurentSaloon().getScrumMaster().equals(Objects.requireNonNull(auth.getCurrentUser()).getEmail())) {
            menu.findItem(R.id.dlt_saloon_btn).setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.us_btn){
            Intent intent = new Intent(this, USActivity.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.info_btn){
            Intent intent = new Intent(this, InfoSaloonView.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.dlt_saloon_btn){
            bdd.deleteDocument(((Constants) ChatActivity.this.getApplication()).getCurentSaloon().getId(),"salon");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (item.getItemId()==R.id.sign_out_saloon){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
