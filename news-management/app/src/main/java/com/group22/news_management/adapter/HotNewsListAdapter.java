package com.group22.news_management.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.view.activity.NewsDetailActivity;
import com.group22.newsmanagerment.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HotNewsListAdapter extends RecyclerView.Adapter<HotNewsListAdapter.ViewHolder> {

    private Context context;
    private List<NewsModel> newsModels;

    public HotNewsListAdapter(Context context, List<NewsModel> newsModels) {
        this.context = context;
        this.newsModels = newsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.trends_rv_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = newsModels.get(position);
        holder.idTVNewsHeading.setText(newsModel.getTitle());
        Picasso.get().load(newsModel.getThumbnail()).into(holder.idIVNews);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("id", newsModel.getId());
                intent.putExtra("link", newsModel.getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView idIVNews;
        TextView idTVNewsHeading;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idIVNews = itemView.findViewById(R.id.idIVNews);
            idTVNewsHeading = itemView.findViewById(R.id.idTVNewsHeading);
        }
    }
}
