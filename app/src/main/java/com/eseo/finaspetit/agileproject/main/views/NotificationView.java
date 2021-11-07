package com.eseo.finaspetit.agileproject.main.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
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
        actionBar.setTitle(getResources().getString(R.string.actionBar_Notification_activity_title));

        binding.listView.setOnItemClickListener((a, v, position, id) -> {
            Object o = binding.listView.getItemAtPosition(position);
            Notification notif = (Notification) o;
            if(notif.getIdSalon()!= null){
                ddb.addMembersToSalon(notif.getIdSalon(),auth.getCurrentUser().getEmail());
                Toast.makeText(NotificationView.this,
                        getResources().getString(R.string.msg_join_saloon_success),
                        Toast.LENGTH_SHORT).show();
            }
            ddb.deleteDocument(notif.getId(),getResources().getString(R.string.database_notification_field));

        });
    }


    @Override
    public void handleNotification(List<Notification> list) {
        CustomClassAdaptater adapt=new CustomClassAdaptater(this,list);
        binding.listView.setAdapter(adapt);
    }
}
