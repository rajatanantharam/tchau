package app.lisboa.lisboapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import app.lisboa.lisboapp.R;


/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signInAnonymously();
    }

    public void createEvent(View view) {
        startActivity(new Intent(MainActivity.this,NewEventActivity.class));
    }
}
