package app.lisboa.lisboapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import app.lisboa.lisboapp.model.Attendee;
import app.lisboa.lisboapp.model.Cache;
import app.lisboa.lisboapp.model.Event;
import app.lisboa.lisboapp.utils.PdfIntentOpener;
import app.lisboa.lisboapp.utils.Utils;


/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList;

    private DatabaseReference mDatabaseReference;
    private FirebaseUser firebaseUser;
    private HashMap<String, String > eventMap;
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
        FirebaseMessaging.getInstance().subscribeToTopic(Utils.getEventTopic());

        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    assert firebaseUser != null;
                    Cache.storeUserId(MainActivity.this,firebaseUser.getUid());
                    eventAdapter = new EventAdapter(MainActivity.this,R.layout.event_adapter,eventList, firebaseUser);
                    eventListView.setAdapter(eventAdapter);
                }
            }
        });

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Event addedEvent = dataSnapshot.getValue(Event.class);
                if(eventList == null) {
                    eventList = new ArrayList<>();
                }
                eventList.add(addedEvent);
                Collections.sort(eventList);

                if(eventMap == null) {
                    eventMap = new HashMap<>();
                }
                eventMap.put(addedEvent.hostId + addedEvent.startTime, dataSnapshot.getKey());
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

        FirebaseDatabase.getInstance().getReference().child(Utils.getEventDatabase()).addChildEventListener(eventListener);
    }

    public void createEvent(View view) {
        Intent intent = new Intent(MainActivity.this,NewEventActivity.class);
        assert firebaseUser != null;
        intent.putExtra("user_id", firebaseUser.getUid());
        startActivity(intent);
    }

    public void goToInfoActivity(View view) {
        PdfIntentOpener.openFile(this, "lisboa_info.pdf");
    }

    public void goToHelpActivity(View view) {
        startActivity(new Intent(MainActivity.this,HelpActivity.class));
    }

    public void onJoinButtonClicked(Event event) {

        if(event.hostId.equalsIgnoreCase(firebaseUser.getUid())) {
            Toast.makeText(MainActivity.this, "You cannot attend the event you created", Toast.LENGTH_SHORT).show();
            return;
        }

        if(Cache.getUserName(this) == null) {
            showUserDialog(event);
        } else {
            updateAttendees(event);
        }
    }

    private void updateAttendees(Event event) {
        DatabaseReference databaseReference = mDatabaseReference.child("events").child(eventMap.get(event.hostId + event.startTime));

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

        Attendee attendee = new Attendee();
        attendee.name = Cache.getUserName(this);
        attendee.userId = Cache.getUserId(this);
        if(event.attendeeNames == null) {
            event.attendeeNames = new ArrayList<>();
        }

        int i = 0, len = event.attendeeNames.size();
        for(i = 0; event.attendeeNames!=null && i< event.attendeeNames.size() ; i++) {
            Attendee user = event.attendeeNames.get(i);
            if(user.userId.equalsIgnoreCase(attendee.userId)) {
                event.attendeeNames.remove(user);
                break;
            }
        }

        if(i >= len) {
            event.attendeeNames.add(attendee);
        }

        HashMap<String, Object> updateNames = new HashMap<>();
        updateNames.put("attendeeNames", event.attendeeNames);
        databaseReference.updateChildren(updateNames);
    }

    private void showUserDialog(final Event event) {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle(R.string.whatsurname);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.input_username, null);
        final EditText input = (EditText) viewInflated.findViewById(R.id.name);
        Button clickedOK = (Button) viewInflated.findViewById(R.id.clickedOK);
        alertDialog.setView(viewInflated);

        clickedOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = input.getText().toString();
                Cache.storeUserName(MainActivity.this, userName);
                updateAttendees(event);
                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    public void onListItemClicked(Event event) {
        Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
        intent.putExtra("event",event);
        intent.putExtra("event_key",eventMap.get(event.hostId + event.startTime));
        startActivity(intent);

    }
}