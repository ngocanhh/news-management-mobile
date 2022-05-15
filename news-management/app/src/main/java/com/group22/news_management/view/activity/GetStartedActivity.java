package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.group22.newsmanagerment.R;

public class GetStartedActivity extends AppCompatActivity {

    private Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        btnStart = findViewById(R.id.btn_start);
        btnStartEventOnClick();
    }

    public void btnStartEventOnClick(){
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetStartedActivity.this,IntroActivity.class);
                startActivity(intent);
            }
        });
    }
}