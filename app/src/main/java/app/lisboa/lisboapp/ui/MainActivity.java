package app.lisboa.lisboapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;
import app.lisboa.lisboapp.utils.PdfIntentOpener;


/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList;

    private DatabaseReference mDatabaseReference;
    private FirebaseUser firebaseUser;
    private HashMap<Event, String > eventMap;
    private EventAdapter eventAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView eventListView = (ListView) findViewById(R.id.eventList);
        eventList = new ArrayList<>();
        eventMap = new HashMap<>();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("What's going on?");
        }

        // push notifications

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/events");

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseUser = mFirebaseAuth.getCurrentUser();
        eventAdapter = new EventAdapter(MainActivity.this,R.layout.event_adapter,eventList, firebaseUser);
        eventListView.setAdapter(eventAdapter);

        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Event addedEvent = dataSnapshot.getValue(Event.class);
                eventList.add(addedEvent);
                Collections.sort(eventList);
                eventMap.put(addedEvent, dataSnapshot.getKey());
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        FirebaseDatabase.getInstance().getReference().child("events").addChildEventListener(eventListener);
    }

    public void createEvent(View view) {
        Intent intent = new Intent(MainActivity.this,NewEventActivity.class);
        assert firebaseUser != null;
        intent.putExtra("user_id", firebaseUser.getUid());
        startActivity(intent);
    }

    public void goToInfoActivity(View view) {
        PdfIntentOpener.openFile(this,"RA_Trouw.pdf");
    }

    public void goToHelpActivity(View view) {
        startActivity(new Intent(MainActivity.this,HelpActivity.class));
    }

    public void onJoinButtonClicked(Event event) {

        if(event.hostId.equalsIgnoreCase(firebaseUser.getUid())) {
            Toast.makeText(MainActivity.this, "You cannot attend the event you created", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference databaseReference = mDatabaseReference.child("events").child(eventMap.get(event));

        if(event.attendees == null) {
            event.attendees = new ArrayList<>();
        }
        if(event.attendees.contains(firebaseUser.getUid())) {
            event.attendees.remove(firebaseUser.getUid());
        } else {
            event.attendees.add(firebaseUser.getUid());
        }

        HashMap<String, Object> update = new HashMap<>();
        update.put("attendees", event.attendees);
        databaseReference.updateChildren(update);
        eventAdapter.notifyDataSetChanged();
    }

    public void onListItemClicked(Event event) {
        Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
        intent.putExtra("event",event);
        startActivity(intent);

    }
}