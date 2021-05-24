package com.example.mics_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        SharedPreferences language = this.getSharedPreferences("language", MODE_PRIVATE);
        SharedPreferences.Editor editor = language.edit();

        Button french_button = (Button) findViewById(R.id.button2);
        Button english_button = (Button) findViewById(R.id.button3);

        french_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("language_set", "fr");
                editor.apply();
                Toast.makeText(LanguageActivity.this, R.string.set_language_french, Toast.LENGTH_LONG).show();
            }
        });
        english_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("language_set", "en");
                editor.apply();
                Toast.makeText(LanguageActivity.this, R.string.set_language_english, Toast.LENGTH_LONG).show();
            }
        });
    }
}