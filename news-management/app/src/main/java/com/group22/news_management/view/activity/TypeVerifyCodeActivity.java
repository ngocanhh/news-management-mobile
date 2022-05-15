package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.group22.newsmanagerment.R;

public class TypeVerifyCodeActivity extends AppCompatActivity {

    private PinEntryEditText pinEntryEditText;
    private Button btnSubmitVerifyCode;
    private boolean isValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_verify_code);
        initViews();
        Intent intent = getIntent();
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String verifyCode = intent.getStringExtra("verifyCode");
        if (pinEntryEditText != null) {
            pinEntryEditText.setOnPinEnteredListener(str -> {
                if (str.toString().equals(verifyCode)) {
                    isValid = true;
                } else {
                    Toast.makeText(TypeVerifyCodeActivity.this, "Mã xác nhận không đúng!", Toast.LENGTH_SHORT).show();
                    pinEntryEditText.setText(null);
                }
            });
        }
        btnSubmitVerifyCodeEventOnClick(phoneNumber);
    }

    private void btnSubmitVerifyCodeEventOnClick(String phoneNumber) {
        btnSubmitVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid){
                    Intent intent = new Intent(TypeVerifyCodeActivity.this, ResetPasswordActivity.class);
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);
                }
            }
        });
    }

    private void initViews() {
        pinEntryEditText = findViewById(R.id.txt_pin_entry);
        btnSubmitVerifyCode = findViewById(R.id.btnSubmitVerifyCode);
    }

}