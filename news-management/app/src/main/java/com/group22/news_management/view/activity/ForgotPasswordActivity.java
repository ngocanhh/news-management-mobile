package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.UserModel;
import com.group22.newsmanagerment.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Button btnSendMessage;
    private EditText edtPhoneNumber;
    private TextView txtAlertPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initViews();
        btnSendMessageEventOnClick();
    }

    private void btnSendMessageEventOnClick() {
        btnSendMessage.setOnClickListener(view -> {
            String phoneNumber = edtPhoneNumber.getText().toString();
            NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
            newsManagementAPI.callSendVerificationCode(phoneNumber).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    int statusCode = response.code();
                    if(statusCode == 400){
                        txtAlertPhoneNumber.setVisibility(View.VISIBLE);
                    }else {
                        UserModel userModel = response.body();
                        txtAlertPhoneNumber.setVisibility(View.GONE);
                        Intent intent = new Intent(ForgotPasswordActivity.this, TypeVerifyCodeActivity.class);
                        intent.putExtra("phoneNumber", phoneNumber);
                        intent.putExtra("verifyCode", userModel.getVerifyCode());
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.d("error", t.toString());
                }
            });
        });
    }

    private void initViews() {
        btnSendMessage = findViewById(R.id.btnSendMessage);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        txtAlertPhoneNumber = findViewById(R.id.txtAlertPhoneNumber);
    }
}