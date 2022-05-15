package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.UserModel;
import com.group22.newsmanagerment.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText edtNewPassword, edtRetypeNewPassword;
    private Button btnSubmitNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phoneNumber");
        btnSubmitNewPasswordEventOnClick(phoneNumber);
    }

    private void btnSubmitNewPasswordEventOnClick(String phoneNumber) {
        btnSubmitNewPassword.setOnClickListener(view -> {
            UserModel userModel = new UserModel();
            userModel.setPhoneNumber(phoneNumber);
            userModel.setPassword(edtNewPassword.getText().toString());
            NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
            newsManagementAPI.callResetPasswordApi(userModel).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    UserModel user = response.body();
                    if(user != null){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ResetPasswordActivity.this);
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Thông báo!");
                        alertDialog.setMessage("Thay đổi mật khẩu thành công");
                        alertDialog.setPositiveButton("Xác nhận", (dialogInterface, i) -> {
                            Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        });
                        alertDialog.show();
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Toast.makeText(ResetPasswordActivity.this, "fail", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initViews() {
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtRetypeNewPassword = findViewById(R.id.edtRetypeNewPassword);
        btnSubmitNewPassword = findViewById(R.id.btnSubmitNewPassword);
    }
}