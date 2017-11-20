package com.example.android.procastinator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.procastinator.mainpage.username;

/**
 * Created by Kevin on 11/18/2017.
 */

public class activityTracker extends AppCompatActivity{
    private FirebaseAuth auth;
    TextView addName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(activityTracker.this, loginpage.class));
            finish();
        }

        setContentView(R.layout.activitytracker);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, AppUsageStatisticsFragment.newInstance())
                    .commit();
        }

        //set title bar
        addName = (TextView) findViewById(R.id.username);
        addName.setText(username + "'s Activity Statement");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.general_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //send user back to main page
    public void goBack(View v){
        startActivity(new Intent(activityTracker.this, mainpage.class));
        finish();
    }

}
