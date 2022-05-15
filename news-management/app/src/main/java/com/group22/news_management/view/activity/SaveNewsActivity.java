package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.group22.news_management.adapter.SavedNewsListAdapter;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveNewsActivity extends AppCompatActivity {

    private RecyclerView savedNewsRecyclerView;
    private List<NewsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        initViews();
        Session session = new Session(this);
        UserModel userModel = session.get();
        NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
        newsManagementAPI.callSavedNewsListApi(userModel.getId()).enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                list = response.body();
                LinearLayoutManager layoutManager = new LinearLayoutManager(SaveNewsActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                savedNewsRecyclerView.setLayoutManager(layoutManager);
                SavedNewsListAdapter adapter = new SavedNewsListAdapter(SaveNewsActivity.this, list);
                savedNewsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        savedNewsRecyclerView = findViewById(R.id.savedNewsRecyclerView);
    }
}