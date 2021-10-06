package com.example.cammaster;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    // display time of onboarding screen
    private static int TIME_OUT = 3000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide title bar of onboarding screen
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // move from onborading activity to login
//                Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
                onStarted();
            }
        }, TIME_OUT);
    }

    public void onStarted() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else
        {
            Intent intent = new Intent(getApplicationContext(),GalleryActivity.class);
            intent.putExtra("month", "0");
            intent.putExtra("year", "0");
            startActivity(intent);
        }
    }
}