package app.lisboa.lisboapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */
public class NewEventActivity extends AppCompatActivity {

    private EditText host, eventType, eventLocation, duration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        host = (EditText)findViewById(R.id.host);
        eventType = (EditText)findViewById(R.id.eventType);
        eventLocation = (EditText)findViewById(R.id.eventLocation);
        duration = (EditText)findViewById(R.id.duration);

    }

    public void broadCastEvent(View view) {

        if(host.getText() == null || host.getText().toString().equalsIgnoreCase("")
                || eventType.getText() == null || eventType.getText().toString().equalsIgnoreCase("")
                || eventLocation.getText() == null || eventLocation.getText().toString().equalsIgnoreCase("")
                || duration.getText() == null || duration.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(this,"Please fill up all the fields",Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");
        eventsRef.push().setValue(new Event(host.getText().toString(),eventType.getText().toString(),eventLocation.getText().toString(),0D,0D,
                new Date().getTime(),Integer.parseInt(duration.getText().toString())));
        finish();
    }
}