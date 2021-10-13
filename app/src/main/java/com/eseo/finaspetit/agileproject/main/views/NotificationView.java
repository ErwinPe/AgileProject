package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.R;
import com.eseo.finaspetit.agileproject.databinding.ActivityNotificationBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptater;
import com.eseo.finaspetit.agileproject.main.interfaces.NotificationsViewsInterface;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class NotificationView extends AppCompatActivity implements NotificationsViewsInterface {
    private ActivityNotificationBinding binding;
    private Database ddb=new Database();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        ddb.getAllNotif(this,auth.getCurrentUser().getEmail());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notifications : ");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = binding.listView.getItemAtPosition(position);
                Notification notif = (Notification) o;
                if(notif.getIdSalon()!= null){
                    ddb.addMembersToSalon(notif.getIdSalon(),auth.getCurrentUser().getEmail());
                }
                ddb.deleteDocument(notif.getId(),"notification");
                //TODO: Faire retour sur la page précédente
            }
        });

        binding.button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Notification notif=new Notification("0","test",null,"erwinpetit7@gmail.com","test",null);
                ddb.createDocument(notif,"notification");
            }
        });
    }


    @Override
    public void handleNotification(List<Notification> list) {
        CustomClassAdaptater adapt=new CustomClassAdaptater(this,list);
        binding.listView.setAdapter(adapt);
    }
}
