package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.group22.news_management.dao.RoleDAO;
import com.group22.news_management.database.DatabaseHelper;
import com.group22.newsmanagerment.R;

public class IntroActivity extends AppCompatActivity {

    private Button btnSignUp, btnLogin, btnLater;
    private RoleDAO roleDAO;
    private DatabaseHelper databaseHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initViews();
        btnSignUpEventOnClick();
        btnLoginEventOnClick();
        btnLaterEventOnClick();
    }

    private void initViews() {
        btnLater = findViewById(R.id.btn_later);
        btnSignUp =  findViewById(R.id.btn_signup);
        btnLogin= findViewById(R.id.btn_signin);
    }

    private void btnLaterEventOnClick() {
        btnLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void btnLoginEventOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void btnSignUpEventOnClick() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}