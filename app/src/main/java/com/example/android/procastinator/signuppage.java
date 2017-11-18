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

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by Kevin on 11/16/2017.
 */

public class signuppage extends AppCompatActivity{
    EditText mail,mophone,pswd,usrusr;
    TextView sup;
    FirebaseAuth auth;


    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            //FirebaseUser currentUser = auth.getCurrentUser();
           // updateUI(currentUser);
           startActivity(new Intent(signuppage.this, mainpage.class));
            finish();
        }

        setContentView(R.layout.signup);

        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        mail = (EditText) findViewById(R.id.mail);
        mophone = (EditText) findViewById(R.id.mobphone);
        sup = (TextView) findViewById(R.id.sup);

        sup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String email = mail.getText().toString().trim();
                final String password = pswd.getText().toString().trim();
                final String username = usrusr.getText().toString();
                final String phone = mophone.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email address Required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter Phone Number!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(signuppage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(signuppage.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //signup successful
                                    final FirebaseUser currentUser = auth.getCurrentUser();

                                    String user_id = currentUser.getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference();
                                    current_user_db.child("Users").child(user_id).child("email").setValue(email);
                                    current_user_db.child("Users").child(user_id).child("username").setValue(username);
                                    current_user_db.child("Users").child(user_id).child("phonenumber").setValue(phone);

                                    FirebaseAuth.AuthStateListener AuthListener = new FirebaseAuth.AuthStateListener(){
                                        @Override
                                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                                            if(currentUser != null){
                                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username).build();
                                                currentUser.updateProfile(profileUpdates);
                                            }
                                        }
                                    };

                                    startActivity(new Intent(signuppage.this, mainpage.class));
                                    finish();
                                }
                            }
                        });
            }
        });


    }
    public void returnLoginPage(View v){
        Intent it = new Intent(signuppage.this,loginpage.class);
        startActivity(it);
        finish();
    }


}
