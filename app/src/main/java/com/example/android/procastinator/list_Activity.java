package com.example.android.procastinator;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.preference.DialogPreference;
import android.provider.DocumentsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;



import static com.example.android.procastinator.mainpage.username;

/**
 * Created by Kevin on 11/19/2017.
 */

public class list_Activity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    TextView addName;
    ImageView add_todo;
    private ListView mTaskListView;
    private ArrayAdapter mAdapter;
    String user_id,courseName,dueDate,taskName;
    DatabaseReference myRef;
    FirebaseUser currentUser;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(list_Activity.this, loginpage.class));
            finish();
        }

        setContentView(R.layout.todo_list);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        currentUser = auth.getCurrentUser();
        user_id = currentUser.getUid();

        //set title bar
        addName = (TextView) findViewById(R.id.username);
        addName.setText(username + "'s TO DO List");

        mTaskListView = (ListView) findViewById(R.id.list);

        //set to do functionality
     /**   add_todo = (ImageView) findViewById(R.id.action_add_task);
        add_todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater factory = LayoutInflater.from(list_Activity.this);
                //text_entry is an Layout XML file containing two text field to display in alert dialog
                final View textEntryView = factory.inflate(R.layout.text_entry, null);

                final EditText input1 = (EditText) textEntryView.findViewById(R.id.EditText1);
                final EditText input2 = (EditText) textEntryView.findViewById(R.id.EditText2);
                final EditText input3 = (EditText) textEntryView.findViewById(R.id.EditText3);


                final AlertDialog.Builder alert = new AlertDialog.Builder(list_Activity.this);
                alert.setTitle("Add TODO:").setView(textEntryView).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                //  Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                                //   Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                                //  Log.i("AlertDialog","TextEntry 2 Entered "+input3.getText().toString());

                                addTODO(input1.getText().toString(),input2.getText().toString(),input3.getText().toString());
                                //updateUI();
                                //save to database

                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //close dialog

                            }
                        });
                alert.show();
            }
        });**/

        updateUI();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.general_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void updateUI(){
        final ArrayList<User> userList = new ArrayList<>();
        //final ArrayList<String> taskList = new ArrayList<>();

        //pull from database
        Query query = myRef.child("Users").child(user_id).child("TO DO");


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {

                    //String task = ds.getValue().toString();
                    if( ds.child("Course Name").getValue() != null && ds.child("Task Name").getValue() != null && ds.child("Due Date").getValue() != null){
                        courseName = ds.child("Course Name").getValue().toString();
                        taskName = ds.child("Task Name").getValue().toString();
                        dueDate = ds.child("Due Date").getValue().toString();

                         user = new User();
                         userList.add(user);
                       //  user.setUserInformation(task);
                         user.setCourseName(courseName);
                         user.setDueDate(dueDate);
                         user.setTaskName(taskName);

                    }
                  //  taskList.add(task);
                    // Log.i("AlertDialog","Due Date Entered "+ dueDate);
                    // Log.i("AlertDialog","Course Name Entered "+ courseName);
                    // Log.i("AlertDialog","Task Name Entered "+ taskName);
                    //Log.i("AlertDialog","Task Entered "+ task);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        if (mAdapter == null) {
          //   mAdapter = new ArrayAdapter<>(this,
          //   R.layout.list_item,
          //   R.id.task_title,
          //   taskList);
            mTaskListView.setAdapter(mAdapter);
            mAdapter = new UserAdapter(this,R.layout.list_item, userList);
            mTaskListView.setAdapter(mAdapter);

        } else {
            mAdapter.clear();
            mAdapter.addAll(userList);
            mAdapter.notifyDataSetChanged();
        }

    }



    public void imageClick(View v){
        LayoutInflater factory = LayoutInflater.from(list_Activity.this);
        //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.text_entry, null);

        final EditText input1 = (EditText) textEntryView.findViewById(R.id.EditText1);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.EditText2);
        final EditText input3 = (EditText) textEntryView.findViewById(R.id.EditText3);


        final AlertDialog.Builder alert = new AlertDialog.Builder(list_Activity.this);
        alert.setTitle("Add TODO:").setView(textEntryView).setPositiveButton("Save",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        //  Log.i("AlertDialog","TextEntry 1 Entered "+input1.getText().toString());
                        //   Log.i("AlertDialog","TextEntry 2 Entered "+input2.getText().toString());
                        //  Log.i("AlertDialog","TextEntry 2 Entered "+input3.getText().toString());

                        addTODO(input1.getText().toString(),input2.getText().toString(),input3.getText().toString());
                        updateUI();
                        //save to database

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        //close dialog

                    }
                });
        alert.show();
    }





    //send user back to main page
    public void goBack(View v){
        startActivity(new Intent(list_Activity.this, mainpage.class));
        finish();
    }

    public void addTODO(String item_name, String course_name, String due_date ){
        try{
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            db.child("Users").child(user_id).child("TO DO").child(item_name).child("Task Name").setValue(item_name);
            db.child("Users").child(user_id).child("TO DO").child(item_name).child("Course Name").setValue(course_name);
            db.child("Users").child(user_id).child("TO DO").child(item_name).child("Due Date").setValue(due_date);

        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public void deleteTask(View v){
        View parent = (View) v.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        Log.i("AlertDialog","Task: "+task);

        Query delete = myRef.child("Users").child(user_id).child("TO DO").orderByChild("Task Name").equalTo(task);

        delete.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ds.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        updateUI();
    }

}
