package com.group22.news_management.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.group22.news_management.adapter.CategoryListAdapter;
import com.group22.news_management.adapter.NewsListAdapter;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.Categories;
import com.group22.news_management.model.CategoryModel;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.utils.StringHandler;
import com.group22.news_management.utils.XMLDOMParser;
import com.group22.news_management.view.HomeView;
import com.group22.newsmanagerment.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements HomeView {

    BottomNavigationItemView icNews, icUserProfile, icExtension, icTrending;
    private RecyclerView newsList;
    private List<NewsModel> newsModels;
    private NewsListAdapter newsListAdapter;
    private CategoryListAdapter categoryListAdapter;
    private List<CategoryModel> categories;
    private RecyclerView categoryRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        newsModels = new ArrayList<>();
        newsListAdapter = new NewsListAdapter(HomeActivity.this, newsModels);
        newsList.setAdapter(newsListAdapter);
        icProfileEventOnClick();
        icNewsEventOnClick();
        icExtensionEventOnClick();
        icTrendingEventOnClick();
        new ReadRSS().execute("https://vnexpress.net/rss/tin-moi-nhat.rss");
        NewsManagementAPI.newsManagementAPI.callCategoryListApi().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                categories = response.body().getList();
                categoryListAdapter = new CategoryListAdapter(HomeActivity.this, categories, newsList);
                categoryRecyclerview.setAdapter(categoryListAdapter);
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                Log.d("error: ", t.toString());
            }
        });
    }

    private void icTrendingEventOnClick() {
        icTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrendingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void icExtensionEventOnClick() {
        icExtension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, WeatherForecastActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        newsList = findViewById(R.id.recycleViewNewsList);
        icUserProfile = findViewById(R.id.personal_page);
        icNews = findViewById(R.id.icon_paper);
        icExtension = findViewById(R.id.extension_page);
        icTrending = findViewById(R.id.trending_page);
        categoryRecyclerview = findViewById(R.id.categoryRecyclerview);
    }

    private class ReadRSS extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDes = document.getElementsByTagName("description");
            for(int i = 0; i < nodeList.getLength(); i++){
                NewsModel newsModel = new NewsModel();
                Element element = (Element) nodeList.item(i);
                String cdata = nodeListDes.item(i + 1).getTextContent();
                newsModel.setTitle(parser.getValue(element, "title"));
                try {
                    newsModel.setDescription(cdata.split("</br>")[1]);
                    newsModel.setThumbnail(StringHandler.getThumbnailSRC(cdata));
                }catch (ArrayIndexOutOfBoundsException e){
                    newsModel.setDescription(cdata);
                    newsModel.setThumbnail("#");
                }
                newsModel.setContent(parser.getValue(element, "link"));
                newsModels.add(newsModel);
            }
            newsListAdapter.notifyDataSetChanged();
        }
    }

    private void icProfileEventOnClick() {
        icUserProfile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
            startActivity(intent);
        });
    }

    private void icNewsEventOnClick() {
        icNews.setOnClickListener(view -> {
            finish();
            startActivity(getIntent());
        });
    }

}