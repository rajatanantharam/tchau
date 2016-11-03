package app.lisboa.lisboapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;


/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class MainActivity extends AppCompatActivity {

    private ListView eventListView;
    private List<Event> eventList;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("What's going on?");
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInAnonymously();

        eventListView = (ListView)findViewById(R.id.eventList);
        eventList = new ArrayList<>();

        final EventAdapter eventAdapter = new EventAdapter(this,R.layout.event_adapter,eventList);
        eventListView.setAdapter(eventAdapter);

        ChildEventListener eventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Event addedEvent = dataSnapshot.getValue(Event.class);
                eventList.add(addedEvent);
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
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        assert user != null;
        intent.putExtra("user_id",user.getUid());
        startActivity(intent);
    }

    public void goToInfoActivity(View view) {
    }

    public void goToHelpActivity(View view) {
    }
}
