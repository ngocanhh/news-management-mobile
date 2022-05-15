package com.group22.news_management.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.NewsModel;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SavedNewsListAdapter extends RecyclerView.Adapter<SavedNewsListAdapter.ViewHolder> {

    private Context context;
    private List<NewsModel> nesNewsModels;

    public SavedNewsListAdapter(Context context, List<NewsModel> nesNewsModels) {
        this.context = context;
        this.nesNewsModels = nesNewsModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.saved_news_rv_items, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel newsModel = nesNewsModels.get(position);
        holder.txtNewsHeading.setText(newsModel.getTitle());
        Picasso.get().load(newsModel.getThumbnail()).into(holder.thumbnail);
        holder.itemView.setOnLongClickListener(view -> {
            Session session = new Session(context);
            long newsId = newsModel.getId();
            long userId = session.get().getId();
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                alertDialog.setCancelable(false);
            alertDialog.setTitle("Thông báo!");
            alertDialog.setMessage("Bạn muốn bỏ lưu bài viết?");
            alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
                newsManagementAPI.callDeleteSavedNewsApi(userId, newsId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        nesNewsModels.remove(newsModel);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("error", t.toString());
                    }
                });
            });
            alertDialog.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return nesNewsModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail;
        TextView txtNewsHeading;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.idIVNews);
            txtNewsHeading = itemView.findViewById(R.id.idTVNewsHeading);
        }
    }
}
