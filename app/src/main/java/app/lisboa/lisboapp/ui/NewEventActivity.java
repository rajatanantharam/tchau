package app.lisboa.lisboapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Cache;
import app.lisboa.lisboapp.model.Event;
import app.lisboa.lisboapp.utils.NotificationBuilder;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */
public class NewEventActivity extends AppCompatActivity {

    private EditText host, eventType, eventLocationView, duration;
    private static final int PLACE_PICKER_REQUEST = 1;
    private Double lat = 0D, lon = 0D;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        host = (EditText) findViewById(R.id.host);
        eventType = (EditText) findViewById(R.id.eventType);
        eventLocationView = (EditText) findViewById(R.id.eventLocation);
        duration = (EditText) findViewById(R.id.duration);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Create a meetup story");
        }

        prefillViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void broadCastEvent(View view) {

        if (host.getText() == null || host.getText().toString().equalsIgnoreCase("")
                || eventType.getText() == null || eventType.getText().toString().equalsIgnoreCase("")
                || eventLocationView.getText() == null || eventLocationView.getText().toString().equalsIgnoreCase("")
                || duration.getText() == null || duration.getText().toString().equalsIgnoreCase("")) {

            Toast.makeText(this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");
        Event event = new Event(host.getText().toString(), eventType.getText().toString(), eventLocationView.getText().toString(), lat, lon,
                System.currentTimeMillis() / 1000L , Integer.parseInt(duration.getText().toString()), getIntent().getStringExtra("user_id"));
        eventsRef.push().setValue(event);
        Cache.storeEvent(this, event);
        new NotificationBuilder().send(event);
        finish();

    }

    private void prefillViews() {

        Event event = Cache.getPreviousEvent(this);
        if(event == null) {
            return;
        }

        host.setText(event.hostName);
        eventType.setText(event.eventName);
        duration.setText(String.valueOf(event.durationInMinutes));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                lat = place.getLatLng().latitude;
                lon = place.getLatLng().longitude;
                if(place.getName() != null) {
                    eventLocationView.setText(String.valueOf(place.getName()));
                }
            }
        }
    }

    public void pickLocation(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
    }
}