package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.eseo.finaspetit.agileproject.databinding.ActivityNotificationBinding;
import com.eseo.finaspetit.agileproject.main.components.CustomClassAdaptater;
import com.eseo.finaspetit.agileproject.main.interfaces.NotificationsViewsInterface;
import com.eseo.finaspetit.agileproject.main.library.Database;
import com.eseo.finaspetit.agileproject.main.library.Notification;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;
import java.util.Objects;

public class NotificationView extends AppCompatActivity implements NotificationsViewsInterface {
    private ActivityNotificationBinding binding;
    private final Database ddb=new Database();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);
        ddb.getAllNotif(this, Objects.requireNonNull(auth.getCurrentUser()).getEmail());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Notifications : ");

        binding.listView.setOnItemClickListener((a, v, position, id) -> {
            Object o = binding.listView.getItemAtPosition(position);
            Notification notif = (Notification) o;
            if(notif.getIdSalon()!= null){
                ddb.addMembersToSalon(notif.getIdSalon(),auth.getCurrentUser().getEmail());
                Toast.makeText(NotificationView.this,
                        "Salon rejoint ! ",
                        Toast.LENGTH_SHORT).show();
            }
            ddb.deleteDocument(notif.getId(),"notification");

        });

        binding.button2.setOnClickListener(v -> {
            Notification notif=new Notification("0","test",null,"erwinpetit7@gmail.com","test",null);
            ddb.createDocument(notif,"notification");
        });
    }


    @Override
    public void handleNotification(List<Notification> list) {
        CustomClassAdaptater adapt=new CustomClassAdaptater(this,list);
        binding.listView.setAdapter(adapt);
    }
}
