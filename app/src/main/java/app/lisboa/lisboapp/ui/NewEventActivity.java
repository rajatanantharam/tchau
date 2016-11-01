package app.lisboa.lisboapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */
public class NewEventActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
    }

    public void broadCastEvent(View view) {
        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");
        eventsRef.push().setValue(new Event("testName", "testHostName", 0.0, "testLocationName", 0.0, "testTimeStamp"));
    }
}