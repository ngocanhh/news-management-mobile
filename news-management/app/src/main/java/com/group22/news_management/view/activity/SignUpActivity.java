package com.group22.news_management.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.presenter.SignUpPresenter;
import com.group22.news_management.presenter.impl.SignUpPresenterImpl;
import com.group22.news_management.utils.Session;
import com.group22.news_management.view.SignUpView;
import com.group22.newsmanagerment.R;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    private Button btnSignUp, btnSignIn;
    private EditText editTextUsername, editTextPassword, editTextPhoneNumberSuffix, editTextRetypePassword;
    private TextView txtAlertTK, txtAlertMK, txtAlertRPMK;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        signUpPresenter = new SignUpPresenterImpl(this);
        btnSignUpEventOnClick();
        btnSignInEventOnClick();
    }

    private void btnSignInEventOnClick() {
        btnSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btn_signin1);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPhoneNumberSuffix = findViewById(R.id.editTextPhoneNumberSuffix);
        editTextRetypePassword = findViewById(R.id.editTextRetypePassword);
        txtAlertTK = findViewById(R.id.txtAlertTK);
        txtAlertMK = findViewById(R.id.txtAlertMK);
        txtAlertRPMK = findViewById(R.id.txtAlertRPMK);
    }

    private void btnSignUpEventOnClick() {
        btnSignUp.setOnClickListener(view -> {
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String repeatPassword = editTextRetypePassword.getText().toString();
            String phoneNumber = editTextPhoneNumberSuffix.getText().toString();
            boolean isValidData = checkValidData(username, password, repeatPassword);
            if(isValidData){
                signUpPresenter.signUp(username, password, phoneNumber);
            }
        });
    }

    private boolean checkValidData(String username, String password, String repeatPassword){
        boolean isValidData = true;
        if(username.isEmpty()){
            txtAlertTK.setVisibility(View.VISIBLE);
            isValidData = false;
        }else {
            txtAlertTK.setVisibility(View.GONE);
        }
        if(password.isEmpty()){
            txtAlertMK.setVisibility(View.VISIBLE);
            isValidData = false;
        }else {
            txtAlertMK.setVisibility(View.GONE);
        }
        if(repeatPassword.isEmpty()){
            txtAlertRPMK.setVisibility(View.VISIBLE);
            isValidData = false;
        }else {
            if (!password.equals(repeatPassword)){
                txtAlertRPMK.setText("Xác nhận lại mật khẩu!");
                txtAlertRPMK.setVisibility(View.VISIBLE);
                isValidData = false;
            }else {
                txtAlertTK.setVisibility(View.GONE);
            }
        }
        return isValidData;
    }

    @Override
    public void onSuccess(UserModel userModel) {
        Session session = new Session(SignUpActivity.this);
        session.put(userModel);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SignUpActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Thông báo!");
        alertDialog.setMessage("Đăng ký thành công");
        alertDialog.setPositiveButton("Xác nhận", (dialogInterface, i) -> {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        alertDialog.show();
    }

    @Override
    public void onError() {
        Toast.makeText(SignUpActivity.this,"Username Exists", Toast.LENGTH_SHORT).show();
    }
}