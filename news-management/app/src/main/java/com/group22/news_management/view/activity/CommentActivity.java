package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.group22.news_management.adapter.CommentListAdapter;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.CommentModel;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView recyclerViewListComment;
    private EditText editTextComment;
    private Button btnSubmitComment;
    List<CommentModel> commentModels;
    private CommentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initViews();
        Intent intent = getIntent();
        long newsId = intent.getLongExtra("newsId", 0);
        Session session = new Session(this);
        long userId = session.get().getId();
        getCommentList(newsId);
        btnSubmitCommentEventOnClick(newsId, userId);
    }

    private void getCommentList(long newsId){
        NewsManagementAPI.newsManagementAPI.callGetCommentByNewsIdApi(newsId).enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(Call<List<CommentModel>> call, Response<List<CommentModel>> response) {
                commentModels = response.body();
                adapter = new CommentListAdapter(commentModels, CommentActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CommentActivity.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerViewListComment.setLayoutManager(linearLayoutManager);
                recyclerViewListComment.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CommentModel>> call, Throwable t) {
                Log.d("error: ", t.toString());
            }
        });
    }

    private void btnSubmitCommentEventOnClick(long newsId, long userId) {
        btnSubmitComment.setOnClickListener(view -> {
            String content = editTextComment.getText().toString();
            CommentModel commentModel = new CommentModel();
            commentModel.setNewsId(newsId);
            commentModel.setUserId(userId);
            commentModel.setContent(content);
            commentModel.setCreateDate(new Date());
            NewsManagementAPI.newsManagementAPI.callSaveCommentApi(commentModel).enqueue(new Callback<CommentModel>() {
                @Override
                public void onResponse(Call<CommentModel> call, Response<CommentModel> response) {
                    getCommentList(newsId);
                }

                @Override
                public void onFailure(Call<CommentModel> call, Throwable t) {

                }
            });
        });
    }

    private void initViews() {
        recyclerViewListComment = findViewById(R.id.recyclerViewListComment);
        editTextComment = findViewById(R.id.editTextComment);
        btnSubmitComment = findViewById(R.id.btnSubmitComment);
    }

}