package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.group22.newsmanagerment.R;

public class SettingsActivity extends AppCompatActivity {

        Button back ,next ,next1 ,next2;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            back= findViewById(R.id.btn_back2);
            next= findViewById(R.id.btn_next0);
            next1= findViewById(R.id.btn_next1);
            next2= findViewById(R.id.btn_next2);
            back.setOnClickListener(view -> {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
            });
            next.setOnClickListener(view -> {
                Intent intent = new Intent(SettingsActivity.this,ThemeActivity.class);
                startActivity(intent);
            });
            next1.setOnClickListener(view -> {
                Intent intent = new Intent(SettingsActivity.this,AutoPlayVideoActivity.class);
                startActivity(intent);
            });
            next2.setOnClickListener(view -> {

            });
        }
    }