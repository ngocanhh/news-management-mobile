package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.utils.Session;
import com.group22.news_management.view.LoginView;
import com.group22.newsmanagerment.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements FacebookCallback<LoginResult>, LoginView {

    private Button btnLogin;
    private LoginButton loginFacebookButton;
    private EditText editTextUsername, editTextPassword;
    private TextView txtReminder, txtAlertTK, txtAlertMK, txtAlert, textViewForgotPassword;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        callbackManager = CallbackManager.Factory.create();
        loginFacebookButton.registerCallback(callbackManager, this);
        btnLoginEvenOnClick();
        txtReminderEventOnClick();
        textViewForgotPasswordEventOnClick();
    }

    private void textViewForgotPasswordEventOnClick() {
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void txtReminderEventOnClick() {
        txtReminder.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        btnLogin = findViewById(R.id.btn_login);
        loginFacebookButton = findViewById(R.id.login_button);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        txtAlertTK = findViewById(R.id.txtAlertTK);
        txtAlertMK = findViewById(R.id.txtAlertMK);
        txtAlert = findViewById(R.id.txtAlert);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        txtReminder = findViewById(R.id.reminder);
    }

    private void btnLoginEvenOnClick() {
        btnLogin.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            boolean isValidData = checkValidData(username, password);
            if(isValidData){
                NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
                newsManagementAPI.callLoginApi(username, password).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        UserModel userModel = response.body();
                        if(userModel != null){
                            loginSuccess(userModel);
                        }else {
                            loginError();
                        }

                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "login fail", Toast.LENGTH_SHORT).show();
                        Log.d("error", t.toString());
                    }
                });
            }
        });
    }

    private boolean checkValidData(String username, String password){
        boolean isValidData = true;
        if(username.isEmpty()){
            txtAlertTK.setVisibility(View.VISIBLE);
            isValidData = false;
        }else {
            txtAlertTK.setVisibility(View.GONE);
        }
        if (password.isEmpty()){
            txtAlertMK.setVisibility(View.VISIBLE);
            isValidData = false;
        }else {
            txtAlertMK.setVisibility(View.GONE);
        }
        return isValidData;
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginSuccess(UserModel userModel) {
        Toast.makeText(LoginActivity.this,"Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
        Session session = new Session(this);
        session.put(userModel);
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void loginError() {
        txtAlert.setVisibility(View.VISIBLE);
    }

}