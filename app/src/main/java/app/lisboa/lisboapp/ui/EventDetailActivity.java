package app.lisboa.lisboapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;
import app.lisboa.lisboapp.utils.FundaTextView;

/**
 * Created by Rajat Anantharam on 04/11/16.
 */
public class EventDetailActivity extends AppCompatActivity implements OnMapReadyCallback
{

    private Event event;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.event = (Event) getIntent().getSerializableExtra("event");

        prefillViews();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Event");
        }

    }

    private void prefillViews() {

        ((FundaTextView) findViewById(R.id.eventDescription)).setText(event.eventName + " with " + event.hostName);

        // attendees count

        int count = event.attendees != null ? event.attendees.size() : 0;
        String location = "+ " + count + " others at " + event.locationName;
        ((FundaTextView) findViewById(R.id.eventLocationAndPeople)).setText(location);


        // duration

        Date date = new java.util.Date(event.startTime * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getDefault());

        String eventTime = String.valueOf(simpleDateFormat.format(date));
        String eventDuration = String.valueOf(event.durationInMinutes) + "m";

        ((FundaTextView) findViewById(R.id.eventTime)).setText(eventTime);
        ((FundaTextView) findViewById(R.id.eventDuration)).setText(eventDuration);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(event.latitude,event.longitude)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(event.latitude,event.longitude)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));
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
}
