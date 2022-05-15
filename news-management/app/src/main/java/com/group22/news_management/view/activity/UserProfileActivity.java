package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;

public class UserProfileActivity extends AppCompatActivity {

    private BottomNavigationItemView icNews;
    private TextView textViewUsernameProfile;
    private ImageView imgAvatar;
    private Button btnLogOut;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        initViews();
        // "session" la ten file chua du lieu
        session = new Session(this);
        UserModel userModel = session.get();
        textViewUsernameProfile.setText(userModel.getUsername());
//         cast byte[] -> bitmap -> set ImageBitmap
//        byte[] bytes = userModel.getAvatar();
//        if(bytes != null){
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//            imgAvatar.setImageBitmap(bitmap);
//        }
        icNewsEventOnClick();
        btnLogOutEvenOnClick();
        imgAvatarEventOnClick();
    }

    private void initViews() {
        icNews = findViewById(R.id.icon_paper);
        textViewUsernameProfile = findViewById(R.id.textViewUsernameProfile);
        btnLogOut = findViewById(R.id.btnLogOut);
        imgAvatar = findViewById(R.id.imgAvatar);
    }

    private void icNewsEventOnClick() {
        icNews.setOnClickListener(view -> {
            Intent intent = new Intent(UserProfileActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void btnLogOutEvenOnClick() {
        btnLogOut.setOnClickListener(view -> {
            // remove du lieu truoc khi dang xuat
            session.remove();
            Intent intent = new Intent(UserProfileActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void imgAvatarEventOnClick() {
        imgAvatar.setOnClickListener(view -> {
            Intent intent = new Intent(UserProfileActivity.this, UpdateAvatarActivity.class);
            startActivity(intent);
        });
    }

}