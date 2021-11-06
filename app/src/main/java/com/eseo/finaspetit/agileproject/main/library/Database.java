package com.eseo.finaspetit.agileproject.main.library;

import android.util.Log;
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
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    public Database(){

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

    public void addUSToSalon(US us){
        createDocument(us,"us");
        System.out.println("US ajoutée: "+us.toString());
    }

    public void addMessageToUSChat(Message message, String idUS){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("us").document(idUS);
        washingtonRef.update("messages", FieldValue.arrayUnion(message));
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
        String TAG="ok";
        ArrayList<US> usList=new ArrayList<>();

        firestore.collection("us").whereEqualTo("idSalon",idSalon).get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map data= document.getData();
                            US usToAdd=new US(document.getId(),(String)data.get("nom"),(String)data.get("description"),(boolean) data.get("voted"),(Timestamp)data.get("dateCreation"),(String)data.get("etat"),idSalon);
                            usList.add(usToAdd);
                        }
                    } else {
                        System.out.println( "Error getting documents: "+ task.getException());
                    }
                    ((UsViewInterface)act).handleUS(usList);
                }
            });


    }

    public void gestUs(AppCompatActivity act,String idSalon, String idUS) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document(idSalon);
        String TAG="ok";
        ArrayList<Message> listMessage = new ArrayList<>();


        firestore.collection("us").document(idUS).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot.exists()) {
                    DocumentSnapshot document = snapshot;
                    HashMap<String, Object> us = (HashMap<String, java.lang.Object>) document.getData();
                    US newUS =new US(document.getId(),(String)us.get("nom"),(String)us.get("description"),(boolean)us.get("voted"),(Timestamp) us.get("dateCreation"),(String)us.get("etat"),(String)us.get("idSalon"));

                    ((ChatUSViewInterface)act).gestBtnVote(newUS);

                }

            }
        });

    }


    public void getAllMessagesFromUS(AppCompatActivity act,String idSalon, String idUS) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("salon").document(idSalon);
        String TAG="ok";
        ArrayList<Message> listMessage = new ArrayList<>();


        firestore.collection("us").document(idUS).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                ArrayList<Message> listMessage = new ArrayList<>();
                DocumentSnapshot document = snapshot;
                if (snapshot.exists()) {
                    HashMap<String, Object> address = (HashMap<String, java.lang.Object>) document.getData();
                    ArrayList<Object> usHash2 = (ArrayList<Object>) address.get("messages");
                    if(usHash2 !=null){
                        for(int j=0; j< usHash2.size();j++){
                            HashMap<String,Object> n= (HashMap<String, Object>) usHash2.get(j);
                            listMessage.add(new Message((String)n.get("messageText"),(String)n.get("messageUser"),(Timestamp) n.get("messageTime")));
                        }
                    }

                    Collections.sort(listMessage, Comparator.comparing(Message::getMessageTime));
                    ((ChatUSViewInterface)act).handleMessageUS(listMessage);

                }

            }
        });

    }

    public void addNoteToUS(Note note, String idUS){
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference washingtonRef = firestore.collection("us").document(idUS);
        washingtonRef.update("notes", FieldValue.arrayUnion(note));
    }

    public void getAllNoteFromUS(AppCompatActivity act, String idUS) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("us").document(idUS);
        String TAG="getAllNoteFromUS";
        ArrayList<Note> listNotes = new ArrayList<>();

        firestore.collection("us").document(idUS).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                DocumentSnapshot document = snapshot;
                if (snapshot.exists()) {
                    HashMap<String, Object> address = (HashMap<String, java.lang.Object>) document.getData();
                    ArrayList<Object> usHash2 = (ArrayList<Object>) address.get("notes");
                    if(usHash2 !=null){
                        for(int j=0; j< usHash2.size();j++){
                            HashMap<String,Object> n= (HashMap<String, Object>) usHash2.get(j);
                            listNotes.add(new Note((int)((long)n.get("note")),(String) n.get("user")));
                        }
                    }
                    Log.w(TAG, "Liste notes: "+ listNotes);
                   //reste a appeler la fonction de ton interface qui gère la liste des notes
                    //((ChatUSViewInterface)act).handleMessageUS(listMessage);

                }

            }
        });

    }

    public void getLowerAndHigherNote(AppCompatActivity act, String idUS){
        String TAG="getLowerAndHigherNote";
        ArrayList<Note> listNotes = new ArrayList<>();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("us").document(idUS).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                DocumentSnapshot document = snapshot;
                if (snapshot.exists()) {
                    HashMap<String, Object> address = (HashMap<String, java.lang.Object>) document.getData();
                    ArrayList<Object> usHash2 = (ArrayList<Object>) address.get("notes");
                    if(usHash2 !=null){
                        HashMap<String,Object> firstNote= (HashMap<String, Object>) usHash2.get(0);
                        Note noteHigh=new Note((int)((long)firstNote.get("note")),(String) firstNote.get("user"));
                        Note noteLow=new Note((int)((long)firstNote.get("note")),(String) firstNote.get("user"));
                        if(usHash2.size()>1){
                            for(int j=1; j< usHash2.size();j++){
                                HashMap<String,Object> n= (HashMap<String, Object>) usHash2.get(j);
                                if((int)((long)n.get("note"))>noteHigh.getNote()){
                                    noteHigh=new Note((int)((long)n.get("note")),(String) n.get("user"));
                                }
                                if((int)((long)n.get("note"))<noteLow.getNote()){
                                    noteLow=new Note((int)((long)n.get("note")),(String) n.get("user"));
                                }

                                listNotes.add(noteLow);
                                listNotes.add(noteHigh);
                            }
                        }

                    }
                    Log.w(TAG, "Liste notes: "+ listNotes);
                    //reste a appeler la fonction de ton interface qui gère la liste des notes
                    //((ChatUSViewInterface)act).handleMessageUS(listMessage);

                }

            }
        });
    }

}
