package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.group22.newsmanagerment.R;

public class AutoPlayVideoActivity extends AppCompatActivity {
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoplay_video);
        back = (Button) findViewById(R.id.btn_back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AutoPlayVideoActivity.this, SettingsActivity.class);

                startActivity(intent);
            }
        });
    }
}
