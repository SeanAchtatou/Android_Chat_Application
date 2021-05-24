package com.example.mics_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String USER = "USER";                                                       // Key to be used when passing data through activities
    private static final int REQUEST_ENABLE_BT = 1;
    public static View view;
    public List<String> users = new ArrayList<>();                                                  // Array for the users detected by the BlueTooth



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        users.add("User1");
        users.add("User2");
        users.add("User3");
        view = findViewById(android.R.id.content);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// Go get the layout from the .activity_main
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Button language_b = (Button) findViewById(R.id.button4);

        IntentFilter filter = new IntentFilter();                                                   // Add a filter that wait to get a broadcast from the bluetooth service
        filter.addAction("service_users_activity");
        BroadcastReceiver b_a = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null)
                    Toast.makeText(context, "I RECEIVED IT YEEE", Toast.LENGTH_LONG).show();
                    Intent in = getIntent();
                    users.add(in.getStringExtra("NAME"));

            }
        };
        registerReceiver(b_a, filter);


        if (bluetoothAdapter == null) {                                                             // Check if the BlueTooth feature is activated on the phone
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.require_bluetooth);
            builder.setMessage(R.string.miss_bluetooth)
                    .setCancelable(false)
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            finishAffinity();
                            System.exit(0); }}).show();
        }
        else{
            if (!bluetoothAdapter.isEnabled()) {
                TextView status = (TextView) findViewById(R.id.textView4);
                status.setText(R.string.offline);
                status.setTextColor(getResources().getColor(R.color.red));
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.require_bluetooth);
                builder.setMessage(R.string.warn_bluetooth)
                        .setCancelable(false)
                        .setPositiveButton(R.string.answer_y, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                            }
                        })
                        .setNegativeButton(R.string.answer_n, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                Context context = getApplicationContext();
                                String text = "BlueTooth is off!";
                                Toast.makeText(context,text,Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
            else{
                TextView status = (TextView) findViewById(R.id.textView4);                          // Set the status of the user to ONLINE
                status.setText(R.string.online);
                status.setTextColor(getResources().getColor(R.color.green));
            }
        }

        language_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LanguageActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        System.out.println("ONSTART");
        super.onStart();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.isEnabled()) {                                                         // If the BlueTooth is enabled launch the search service
            Intent intent = new Intent(this, BlueToothService.class);
            startService(intent);
        }

    }



    @Override
    public void onResume(){
        super.onResume();
        ListView listview = (ListView) findViewById(R.id.listview);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Intent intent = new Intent(this, BlueToothService.class);

        Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable(){                                                         // Each second check the BlueTooth status
            public void run(){
                if (!bluetoothAdapter.isEnabled()) {                                                // If the BlueTooth is OFF Set the status of the user to OFFLINE and stop the searching BlueTooth service and empty the list
                    TextView status = (TextView) findViewById(R.id.textView4);
                    status.setText(R.string.offline);
                    status.setTextColor(getResources().getColor(R.color.red));
                    users.clear();
                    listview.setAdapter(null);
                    stopService(intent);

                }
                else{
                    TextView status = (TextView) findViewById(R.id.textView4);                      // If the BlueTooth is ON Set the status of the user to ONLINE and  and empty the list
                    status.setText(R.string.online);
                    status.setTextColor(getResources().getColor(R.color.green));
                    set_List();

                }
                handler.postDelayed(this,delay);

            }
        },delay);


        /*handler2.postDelayed(new Runnable(){
            public void run(){
                print()
            }
        },delay);*/

    }

    public void set_List(){
        ListView listview = (ListView) findViewById(R.id.listview);                                 // Get the list from the View
        Intent intent = new Intent(this, MessageActivity.class);                     // Create an intent to the message activity to interact with users
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,users);             // Add the users from the list to the adapters
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {      // For each item clicked on the adapter, start the message activity
                intent.putExtra(USER,parent.getItemAtPosition(position).toString());
                startActivity(intent);

            }
        });
    }

}