package com.example.android.procastinator;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.procastinator.mainpage.username;

/**
 * Created by Kevin on 11/19/2017.
 */

public class list_Activity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    TextView addName;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(list_Activity.this, loginpage.class));
            finish();
        }

        setContentView(R.layout.todo_list);

        //set title bar
        addName = (TextView) findViewById(R.id.username);
        addName.setText(username + "'s TO DO List");

        mTaskListView = (ListView) findViewById(R.id.list);
       // updateUI();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Log.d("TAG", "ADD A NEW TASK");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        ArrayList<String> taskList = new ArrayList<>();
        Cursor cursor;
        //pull from database

            mAdapter = new ArrayAdapter<>(this,
                    R.layout.list_item,
                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);

    }





    //send user back to main page
    public void goBack(View v){
        startActivity(new Intent(list_Activity.this, mainpage.class));
        finish();
    }

    public void addTODO(){
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser currentUser = auth.getCurrentUser();
        String user_id = currentUser.getUid();

        try{
          //  DatabaseReference newRef = myRef.child("Users").child(user_id).child("TO DO").push();
           // newRef.setValue("Task 1");
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    }
