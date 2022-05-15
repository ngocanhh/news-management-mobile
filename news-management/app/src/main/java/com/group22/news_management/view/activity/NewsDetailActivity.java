package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.SavedModel;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.utils.HTMLParser;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailActivity extends AppCompatActivity {

    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initViews();
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        long newsId = intent.getLongExtra("id", 0);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setAllowContentAccess(true);
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setAllowFileAccessFromFileURLs(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("file:///android_asset/news.html");
        HTMLParser htmlParser = new HTMLParser(this);
        htmlParser.execute(link);
        bottomAppBarEventOnItemClick(newsId);
    }

    private void bottomAppBarEventOnItemClick(long newsId) {
        bottomAppBar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case R.id.icon_bookmark:{
                    Session session = new Session(this);
                    UserModel userModel = session.get();
                    SavedModel savedModel = new SavedModel();
                    savedModel.setNewsId(newsId);
                    savedModel.setUserId(userModel.getId());
                    NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
                    newsManagementAPI.callSaveNewsApi(savedModel).enqueue(new Callback<SavedModel>() {
                        @Override
                        public void onResponse(Call<SavedModel> call, Response<SavedModel> response) {
                            Toast.makeText(NewsDetailActivity.this, "Da luu bai viet", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<SavedModel> call, Throwable t) {

                        }
                    });
                    Intent intent = new Intent(NewsDetailActivity.this, SaveNewsActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.icon_chat:{
                    Intent intent = new Intent(NewsDetailActivity.this, CommentActivity.class);
                    intent.putExtra("newsId", newsId);
                    startActivity(intent);
                    break;
                }
                case R.id.icon_font_size:{
                    Toast.makeText(NewsDetailActivity.this, "icon_font_size", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            return false;
        });
    }

    private void initViews() {
        bottomAppBar = findViewById(R.id.bottomAppBar);
    }

}