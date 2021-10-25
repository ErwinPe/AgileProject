package com.eseo.finaspetit.agileproject.main.library;

import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.eseo.finaspetit.agileproject.main.interfaces.ChatUSViewInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.ChatViewInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.CreateSaloonViewInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.NotificationsViewsInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.ReadAllMessagesInterface;
import com.eseo.finaspetit.agileproject.main.interfaces.UsViewInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Database {

    public Database(){

    }

    public String getupdate(){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String data="";
        final DocumentReference docRef = firestore.collection("notification").document("abRmGEOuOeXl8kj4zWf2");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println("Listen failed");
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    System.out.println("Current data "+snapshot.getData());
                } else {
                    System.out.println("Current data : null");
                }
            }
        });
        return data;
    }

    public void getAllMessages(AppCompatActivity act,String idSalon) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document(idSalon);
        String TAG="ok";

        docRef.addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                Object group = snapshot.get("chat");
                List<Message> messages=new ArrayList<>();
                for(int i=0;i<((ArrayList<?>) group).size();i++){
                    HashMap<String,Object> test= (HashMap<String, Object>) ((ArrayList<?>) group).get(i);
                    String txt= (String) test.get("messageText");
                    String user= (String) test.get("messageUser");
                    Timestamp tm= (Timestamp) test.get("messageTime");
                    messages.add(new Message(txt,user,tm));
                }
                Collections.sort(messages, Comparator.comparing(Message::getMessageTime));
                ((ChatViewInterface)act).handleMessage(messages);

            }
        });

    }

    public void getAllSalon(AppCompatActivity act,String email) {//Context context
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        firestore.collection("salon").whereArrayContains("members",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Salon> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Salon sal=new Salon(document.getId(),
                                document.getString("nom"),
                                document.getString("description"),
                                document.getString("scrumMaster"),
                                document.getTimestamp("creationDate"));
                        list.add(sal);

                    }
                    ((ReadAllMessagesInterface)act).handleResultAllSalon(list);
                } else {
                    System.out.println("error : "+task.getException());
                }
            }
        });

    }

    //OFFICIEL
    public String createDocument(Object obj, String collection){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestore.collection(collection).document();
        String idRef = docRef.getId();

        System.out.println("IDREF "+idRef);

        firestore.collection(collection).document(idRef).set(obj);
        return idRef;
    }

    public void deleteDocument(String id, String collection){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(collection).document(id).delete();
    }

    public void addMessageToGeneralChat(String idSalon, Message message){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("salon").document(idSalon);
        washingtonRef.update("chat", FieldValue.arrayUnion(message));
    }

    public void addMessageToUSChat(String idSalon,Message message, String idUS){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("salon").document(idSalon);
        DocumentReference usRef= washingtonRef.collection("us").document(idUS);
        System.out.println(usRef.toString());
        /*Map<String, Object> address = (Map<String, Object>) snapshot.getData().get("us");
        List<Message> messages=new ArrayList<>();
        //System.out.println("ici la: "+address.toString());

        if( address != null ){
            HashMap<String,Object> test= (HashMap<String, Object>) (address).get(idUS);
            HashMap<String,Object> messagesUS= (HashMap<String, Object>) (test).get("messages");

            for(int i=1;i<=messagesUS.size();i++){
                HashMap<String,Object> mes= (HashMap<String,Object>)messagesUS.get(i+"");
                //System.out.println("Message n°"+i+" ,"+mes);
                if(mes !=null){
                    String txt= (String) mes.get("messageText");
                    String user= (String) mes.get("messageUser");
                    Timestamp tm= (Timestamp) mes.get("messageTime");
                    messages.add(new Message(txt,user,tm));
                }

            }
        }*/

        //washingtonRef.update("messages", FieldValue.arrayUnion(message));

    }

    public void getNotification(AppCompatActivity act,String email){
        ArrayList<Notification> notif=new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        //DocumentReference docRef = firestore.collection("notification").document("notif1"); //document("qu5rbrhQRw10FTvfAIpw");


        firestore.collection("notification").whereEqualTo("receiver",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Notification> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Notification notif=new Notification(document.getId(),document.getString("message"),document.getTimestamp("dateCreation"),document.getString("receiver"),document.getString("title"),document.getString("idSalon"));
                        list.add(notif);
                        ((NotificationsViewsInterface)act).handleNotification(list);
                    }

                } else {
                    System.out.println("error : "+task.getException());
                }
            }
        });
    }

    public void addMembersToSalon(String idSalon,String userEmail){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("salon").document(idSalon);
        washingtonRef.update("members", FieldValue.arrayUnion(userEmail));
    }

    public void addEmailInDatabaseIfUserInexistant(String email){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("members").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if(task.getResult().isEmpty()){
                        HashMap<String, String> obj=new HashMap<>();
                        obj.put("email",email);
                        createDocument(obj,"members");
                    }
                } else {
                    System.out.println("error : "+task.getException());
                }
            }
        });
    }

    public void getAllUser (AppCompatActivity act){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("members").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    ArrayList<String> listmembers =new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String email = document.getString("email");
                        System.out.println("all USER FOR  " +  email);

                        listmembers.add(email);

                    }
                    ((CreateSaloonViewInterface)act).handleGetAllUser(listmembers);
                } else {
                    System.out.println("error : "+task.getException());
                }
            }
        });

    }

    public void getAllNotif(AppCompatActivity act,String email) {//Context context
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String TAG="ok";
        ArrayList<Notification> allNotif=new ArrayList<>();
        firestore.collection("notification").whereEqualTo("receiver", email).addSnapshotListener(new EventListener<QuerySnapshot>()
        {

            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots,
                                @Nullable FirebaseFirestoreException e) {
                allNotif.clear();
                if (e != null) {
                    Log.w(TAG, "listen:error", e);
                    return;
                }
                //snapshots.getDocuments()
                for (DocumentSnapshot dc : snapshots.getDocuments()) {

                    Notification notif=new Notification(dc.getId(),dc.getString("message"),dc.getTimestamp("dateCreation"),dc.getString("receiver"),dc.getString("title"),dc.getString("idSalon"));
                    allNotif.add(notif);
                }
                ((NotificationsViewsInterface)act).handleNotification(allNotif);

            }
        });

    }

    public void getAllUS(AppCompatActivity act,String idSalon) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document(idSalon);
        String TAG="ok";
        ArrayList<US> us= new ArrayList<>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> address = (Map<String, Object>) document.getData().get("us");
                        if(address != null){
                            for(int i=0;i<address.size();i++){
                                Map<String, Object> usEntity = (Map<String, Object>) address.get(""+i);
                                us.add(new US((String)usEntity.get("nom"),(String)usEntity.get("description"),(HashMap<String,Integer>) usEntity.get("notes"),new ArrayList<Message>(),(boolean) usEntity.get("isVoted"),(Timestamp) usEntity.get("dateCreation"),(String) usEntity.get("etat")));
                            }
                        }
                        ((UsViewInterface)act).handleUS(us);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }


    public void getAllMessagesFromUS(AppCompatActivity act,String idSalon, String idUS) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document(idSalon);
        String TAG="ok";

        docRef.addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                //Object group = snapshot.get("us");
                Map<String, Object> address = (Map<String, Object>) snapshot.getData().get("us");
                List<Message> messages=new ArrayList<>();
                //System.out.println("ici la: "+address.toString());

                if( address != null ){
                    HashMap<String,Object> test= (HashMap<String, Object>) (address).get(idUS);
                    HashMap<String,Object> messagesUS= (HashMap<String, Object>) (test).get("messages");

                    for(int i=1;i<=messagesUS.size();i++){
                        HashMap<String,Object> mes= (HashMap<String,Object>)messagesUS.get(i+"");
                        //System.out.println("Message n°"+i+" ,"+mes);
                        if(mes !=null){
                            String txt= (String) mes.get("messageText");
                            String user= (String) mes.get("messageUser");
                            Timestamp tm= (Timestamp) mes.get("messageTime");
                            messages.add(new Message(txt,user,tm));
                        }

                    }
                }

                Collections.sort(messages, Comparator.comparing(Message::getMessageTime));
                ((ChatUSViewInterface)act).handleMessageUS(messages);

            }
        });

    }

}
