package com.group22.news_management.view.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.presenter.UserPresenter;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAvatarActivity extends AppCompatActivity {

    private ImageView imgCamera, imgFolder, imgUserAvatar;
    private Button btnUpdate, btnCancel;
    private ActivityResultLauncher<Intent> cameraActivityResultLauncher;
    private ActivityResultLauncher<Intent> folderActivityResultLauncher;
    private UserPresenter userPresenter;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_avatar);
        initViews();
        imgCameraEventOnClick();
        imgFolderEventOnClick();
        session = new Session(this);
        byte[] bytes = session.get().getAvatar();
        if(bytes != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imgUserAvatar.setImageBitmap(bitmap);
        }
        cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                        imgUserAvatar.setImageBitmap(bitmap1);
                    }
                });
        folderActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Uri uri = data.getData();
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap2 = BitmapFactory.decodeStream(inputStream);
                            imgUserAvatar.setImageBitmap(bitmap2);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
        btnUpdateEventOnClick();
        btnCancelEventOnClick();
    }

    private void initViews() {
        imgCamera = findViewById(R.id.imgCamera);
        imgFolder = findViewById(R.id.imgFolder);
        imgUserAvatar = findViewById(R.id.imgUserAvatar);
        btnUpdate = findViewById(R.id.btnUpdateAvatar);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void imgCameraEventOnClick() {
        imgCamera.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraActivityResultLauncher.launch(intent);
        });
    }

    private void imgFolderEventOnClick() {
        imgFolder.setOnClickListener(view -> {
            Intent intent =  new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            folderActivityResultLauncher.launch(intent);
        });
    }

    private void btnUpdateEventOnClick() {
        btnUpdate.setOnClickListener(view -> {
            long userId = session.get().getId();
            // cast imageView -> bitmap -> byte[] -> string
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgUserAvatar.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            UserModel userModel = new UserModel();
            userModel.setId(userId);
            userModel.setAvatar(bytes);
            NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
            newsManagementAPI.callUpdateAvatarApi(userModel).enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    Toast.makeText(UpdateAvatarActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.d("error", t.toString());
                }
            });
        });
    }


    private void btnCancelEventOnClick() {
        btnCancel.setOnClickListener(view -> {

        });
    }
}