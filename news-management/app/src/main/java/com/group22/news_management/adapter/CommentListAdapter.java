package com.group22.news_management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group22.news_management.api.NewsManagementAPI;
import com.group22.news_management.model.CommentModel;
import com.group22.news_management.model.UserModel;
import com.group22.news_management.utils.Session;
import com.group22.newsmanagerment.R;
import java.text.SimpleDateFormat;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<CommentModel> comments;
    private Context context;

    public CommentListAdapter(List<CommentModel> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.list_comment_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentModel commentModel = comments.get(position);
        holder.textViewComment.setText(commentModel.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd-MM-yyyy HH:mm:ss");
        String createDate = simpleDateFormat.format(commentModel.getCreateDate());
        holder.textViewTime.setText(createDate);
        holder.textViewCommentUsername.setText(commentModel.getUsername());
        Session session = new Session(context);
        UserModel userModel = session.get();
        if(commentModel.getUsername().equals(userModel.getUsername())){
            holder.textViewDelete.setVisibility(View.VISIBLE);
        }
        holder.textViewDelete.setOnClickListener(view -> {
            comments.remove(commentModel);
            notifyDataSetChanged();
            NewsManagementAPI newsManagementAPI = NewsManagementAPI.newsManagementAPI;
            newsManagementAPI.callDeleteCommentApi(commentModel.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {

                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewComment, textViewTime, textViewCommentUsername, textViewDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewCommentUsername = itemView.findViewById(R.id.textViewCommentUsername);
            textViewDelete = itemView.findViewById(R.id.textViewDelete);
        }
    }
}
