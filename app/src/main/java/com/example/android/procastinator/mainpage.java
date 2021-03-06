package com.example.android.procastinator;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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

import java.util.ArrayList;


/**
 * Created by Kevin on 11/16/2017.
 */

public class mainpage extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference ref;
    private String userID;
    TextView signOut,addName,disturbText;
    public static String username = " ";
    public static String user_email = " ";
    public static String user_phonenumber = " ";
    public boolean disturb;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(mainpage.this, loginpage.class));
            finish();
        }
        setContentView(R.layout.mainpage);
        disturb = false;

        signOut = (TextView) findViewById(R.id.signOut);
        addName = (TextView) findViewById(R.id.username);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //retrieve user data
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //User not logged in
            }
        });

        //sign user out
        signOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(mainpage.this, loginpage.class));
                finish();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.general_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //save user data to User class
    private void showData(DataSnapshot dataSnapshot){
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            User user = new User();
            user.setUsername(ds.child(userID).getValue(User.class).getUsername());
            user.setEmail(ds.child(userID).getValue(User.class).getEmail());
            user.setPhoneNumber(ds.child(userID).getValue(User.class).getPhoneNumber());

            Log.d("OUTPUT", "showData: name: " + user.getUsername());
            Log.d("OUTPUT", "showData: email: " + user.getEmail());
            Log.d("OUTPUT", "showData: phone_num: " + user.getPhoneNumber());

            username = user.getUsername();
            user_email = user.getEmail();
            user_phonenumber = user.getPhoneNumber();
        }

        addName.setText("Welcome, " + username);
    }

    //Send user to activity tracker intent
    public void activityCard(View v){
        startActivity(new Intent(mainpage.this, activityTracker.class));
        finish();
    }

    //send user to to-do list
    public void todoActivity(View v){
        startActivity(new Intent(mainpage.this, list_Activity.class));
        finish();
    }
    public void setDoNotDisturb(View v){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        if(disturb){
            //set phone to turn off do not disturb
            disturb = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
                disturbText = (TextView) findViewById(R.id.disturbText);
                disturbText.setText("Turn on Do Not Disturb");
                disturbText.setTextColor(Color.parseColor("#a6abae"));
            }

        }else{
            //set phone to do not disturb
            disturb = true;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                disturbText = (TextView) findViewById(R.id.disturbText);
                disturbText.setText("DO NOT DISTURB IS ON");
                disturbText.setTextColor(Color.parseColor("#009900"));
            }
        }

    }
    public void websiteBlocker(View v){
        startActivity(new Intent(mainpage.this, website_blocker.class));
        finish();

    }
}
