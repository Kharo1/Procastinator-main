package com.example.android.procastinator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.android.procastinator.mainpage.username;

/**
 * Created by Kevin on 11/25/2017.
 */

public class website_blocker extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    TextView addName;
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(website_blocker.this, loginpage.class));
            finish();
        }
        setContentView(R.layout.website_blocker);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //set title bar
        addName = (TextView) findViewById(R.id.username);
        addName.setText(username + "'s Website Blocker");


        final EditText url = (EditText)findViewById(R.id.url);

        Button deleteButton= (Button)findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String additem= url.getText().toString();
                arrayList.remove(additem);
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Website is on", Toast.LENGTH_SHORT).show();

            }
        });

        Button gotogoogle = (Button)findViewById(R.id.google);
        gotogoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMethod(url);
            }
        });
        listView = (ListView)findViewById(R.id.list);
        String [] website_lists = {};
        arrayList = new ArrayList<>(Arrays.asList(website_lists));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList );
        listView.setAdapter(adapter);
        Button add= (Button)findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //text.setText("working");
                String additem= url.getText().toString();
                arrayList.add(additem);
                adapter.notifyDataSetChanged();
                Toast.makeText(getBaseContext(), "Website is blocked", Toast.LENGTH_SHORT).show();
                // block website

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position+1) + " is selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.general_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void googleMethod(EditText url) {
        Intent i = new Intent(this, webview.class);
        i.putExtra("url",url.getText().toString());
        i.putExtra("list" , String.valueOf(adapter));
        startActivity(i);
    }
    //send user back to main page
    public void goBack(View v){
        startActivity(new Intent(website_blocker.this, mainpage.class));
        finish();
    }

}
