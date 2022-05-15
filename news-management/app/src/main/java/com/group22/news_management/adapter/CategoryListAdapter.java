package com.group22.news_management.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.CategoryModel;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.utils.StringHandler;
import com.group22.news_management.utils.XMLDOMParser;
import com.group22.newsmanagerment.R;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> list;
    private RecyclerView newsRecyclerView;

    public CategoryListAdapter(Context context, List<CategoryModel> list, RecyclerView newsRecyclerView) {
        this.context = context;
        this.list = list;
        this.newsRecyclerView = newsRecyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.categories_rv_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel categoryModel = list.get(position);
        holder.txtCategory.setText(categoryModel.getName());
        holder.itemView.setOnClickListener(view -> {
            String url = "https://vnexpress.net/rss/"+ categoryModel.getCode()+ ".rss";
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    // set charset utf-8 for response
                    byte[] bytes = response.toString().getBytes("ISO-8859-1");
                    response = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                parseXMLDocToNewsModelList(response, categoryModel.getId());
                NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
                newsManagementAPI.callGetNewsListByCategoryCode(categoryModel.getCode()).enqueue(new Callback<List<NewsModel>>() {
                    @Override
                    public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                        List<NewsModel> newsModels = response.body();
                        NewsListAdapter newsListAdapter = new NewsListAdapter(context, newsModels);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        newsRecyclerView.setLayoutManager(linearLayoutManager);
                        newsRecyclerView.setAdapter(newsListAdapter);
                    }

                    @Override
                    public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                        Log.d("error", t.toString());
                    }
                });

            }, error -> {
                Log.d("error: ", error.toString());
            });
            requestQueue.add(stringRequest);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void parseXMLDocToNewsModelList(String s, long categoryId){
        List<NewsModel> list = new ArrayList<>();
        XMLDOMParser parser = new XMLDOMParser();
        Document document = parser.getDocument(s);
        NodeList nodeList = document.getElementsByTagName("item");
        NodeList nodeListDes = document.getElementsByTagName("description");
        for(int i = 0; i < nodeList.getLength(); i++){
            NewsModel newsModel = new NewsModel();
            newsModel.setCategoryId(categoryId);
            Element element = (Element) nodeList.item(i);
            newsModel.setTitle(parser.getValue(element, "title"));
            String cdata = nodeListDes.item(i + 1).getTextContent();
            try {
                newsModel.setDescription(cdata.split("</br>")[1]);
                newsModel.setThumbnail(StringHandler.getThumbnailSRC(cdata));
            }catch (ArrayIndexOutOfBoundsException e){
                newsModel.setDescription(cdata);
                newsModel.setThumbnail("#");
            }
            String link = parser.getValue(element, "link");
            newsModel.setContent(link);
            int index = link.lastIndexOf("-");
            String str = link.substring(index + 1);
            String idStr = str.split(".html")[0];
            try {
                int id = Integer.parseInt(idStr);
                newsModel.setId(id);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            String pubDateStr = parser.getValue(element, "pubDate");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss +0700");
            try {
                Date pubDate = simpleDateFormat.parse(pubDateStr);
                newsModel.setPubDate(pubDate);
            } catch (ParseException e) {
                newsModel.setPubDate(new Date());
            }
            NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
            newsManagementAPI.callSaveNewsApi(newsModel).enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    Log.d("msg", "success");
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    Log.d("error: ", t.toString());
                }
            });
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtCategory;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
        }
    }

}