package com.example.android.procastinator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



/**
 * Created by Kevin on 11/12/2017.
 */

public class loginpage extends AppCompatActivity{
    EditText pswd, usrusr;
    TextView lin;
    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceActivity) {
        super.onCreate(savedInstanceActivity);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(loginpage.this, mainpage.class));
            finish();
        }
        setContentView(R.layout.loginpage);

        usrusr = (EditText) findViewById(R.id.usrusr);
        pswd = (EditText) findViewById(R.id.pswrdd);
        lin = (TextView) findViewById(R.id.lin);

        lin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String email= usrusr.getText().toString();
                final String password = pswd.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(loginpage.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()){
                                    //there was an error
                                    Toast.makeText(loginpage.this, "Invalid Email or Password" , Toast.LENGTH_LONG).show();
                                }else{
                                    //successful login
                                    //FirebaseUser currentUser = auth.getCurrentUser();
                                    //  updateUI(currentUser);
                                    Intent intent = new Intent(loginpage.this,mainpage.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });

            }
        });



    }
    public void signUpActivity(View v){
        Intent intent = new Intent(loginpage.this, signuppage.class);
        startActivity(intent);
        finish();
    }

}
