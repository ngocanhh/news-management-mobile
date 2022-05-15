package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.group22.news_management.adapter.HotNewsListAdapter;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.NewsModel;
import com.group22.newsmanagerment.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<NewsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trending);
        initViews();
        NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
        newsManagementAPI.callGetHotNewsListApi().enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                list = response.body();
                LinearLayoutManager layoutManager = new LinearLayoutManager(TrendingActivity.this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                HotNewsListAdapter adapter = new HotNewsListAdapter(TrendingActivity.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) { }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.idRVNews);
    }
}