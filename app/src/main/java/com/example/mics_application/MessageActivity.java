package com.example.mics_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        Intent intent = getIntent();
        String message = intent.getStringExtra("USER");
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView chat = (TextView) findViewById(R.id.textView5);
        EditText textUser = (EditText) findViewById(R.id.editTextTextPersonName);
        Button button = (Button) findViewById(R.id.button);
        textView.setText(message);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textUser.getText().toString().equals("")) {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());
                    chat.append("[" + formattedDate + "]" + " " + "You:" + " " +textUser.getText().toString() + "\n");
                    textUser.setText("");
                }




            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}