package com.example.android.procastinator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
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
    TextView signOut,addName;
    ArrayList<String> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(mainpage.this, loginpage.class));
            finish();
        }
        setContentView(R.layout.mainpage);

        signOut = (TextView) findViewById(R.id.signOut);
        addName = (TextView) findViewById(R.id.username);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        ref = mFirebaseDatabase.getReference();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //User not logged in
            }
        });




        signOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(mainpage.this, loginpage.class));
                finish();
            }
        });



    }
    private void showData(DataSnapshot dataSnapshot){
        String username = " ";
        for(DataSnapshot ds: dataSnapshot.getChildren()){
            User user = new User();
            user.setUsername(ds.child(userID).getValue(User.class).getUsername());
            user.setEmail(ds.child(userID).getValue(User.class).getEmail());
            user.setPhoneNumber(ds.child(userID).getValue(User.class).getPhoneNumber());

            Log.d("OUTPUT", "showData: name: " + user.getUsername());
            Log.d("OUTPUT", "showData: email: " + user.getEmail());
            Log.d("OUTPUT", "showData: phone_num: " + user.getPhoneNumber());

            info.add(user.getUsername());
            info.add(user.getEmail());
            info.add(user.getPhoneNumber());

            username = user.getUsername();
        }

        addName.setText(username);
    }
}
