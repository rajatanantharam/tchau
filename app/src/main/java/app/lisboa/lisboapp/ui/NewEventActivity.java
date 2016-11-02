package app.lisboa.lisboapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */
public class NewEventActivity extends BaseLocationActivity {

    private EditText host, eventType, eventLocation, duration;
    private static final int PLACE_PICKER_REQUEST = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        host = (EditText)findViewById(R.id.host);
        eventType = (EditText)findViewById(R.id.eventType);
        eventLocation = (EditText)findViewById(R.id.eventLocation);
        duration = (EditText)findViewById(R.id.duration);

    }

    public void broadCastEvent(View view) throws GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);


        if(false) {

            if (host.getText() == null || host.getText().toString().equalsIgnoreCase("")
                    || eventType.getText() == null || eventType.getText().toString().equalsIgnoreCase("")
                    || eventLocation.getText() == null || eventLocation.getText().toString().equalsIgnoreCase("")
                    || duration.getText() == null || duration.getText().toString().equalsIgnoreCase("")) {

                Toast.makeText(this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");

            Double lat = 0D, lon = 0D;
            if (mCurrentLocation != null && mCurrentLocation.hasAccuracy()) {
                lat = mCurrentLocation.getLatitude();
                lon = mCurrentLocation.getLongitude();
            }

            eventsRef.push().setValue(new Event(host.getText().toString(), eventType.getText().toString(), eventLocation.getText().toString(), lat, lon,
                    new Date().getTime(), Integer.parseInt(duration.getText().toString())));
            finish();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this,data);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}