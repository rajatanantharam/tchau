package app.lisboa.lisboapp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import app.lisboa.lisboapp.R;
import app.lisboa.lisboapp.model.Event;


/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ListView eventList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventList = (ListView) findViewById(R.id.eventList);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

    }

    public void createEvent(View view) {

        Event dummyEvent = new Event().getDummyEvent();
        String json =  new Gson().toJson(dummyEvent);
        Log.i(getClass().getSimpleName(),json);
        if(mFirebaseUser == null) {
            mFirebaseAuth.signInAnonymously();
        }
    }
}
