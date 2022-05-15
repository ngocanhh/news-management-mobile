package com.group22.news_management.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.group22.news_management.model.WeatherForecastData;
import com.group22.newsmanagerment.R;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyWeatherForecastListAdapter extends RecyclerView.Adapter<DailyWeatherForecastListAdapter.ViewHolder> {

    private Context context;
    private List<WeatherForecastData> list;


    public DailyWeatherForecastListAdapter(Context context, List<WeatherForecastData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_weather_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherForecastData weatherForecastData = list.get(position);
        long dt = weatherForecastData.getDt();
        Date date = new Date(dt*1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        holder.textViewDateTime.setText(simpleDateFormat.format(date));
        String icon = weatherForecastData.getWeather().get(0).getIcon();
        String urlIcon = "https://openweathermap.org/img/wn/" + icon + ".png";
        Picasso.get().load(urlIcon).into(holder.icWeather);
        float temp = weatherForecastData.getMain().getTemp();
        holder.textViewTempItem.setText( Math.round(temp)+ "°C");
        String description = weatherForecastData.getWeather().get(0).getDescription();
        String firstCharacter = description.substring(0, 1);
        String remaining = description.substring(1);
        description = firstCharacter.toUpperCase() + remaining;
        holder.textViewDescItem.setText(description);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDateTime, textViewTempItem, textViewDescItem;
        ImageView icWeather;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
            textViewTempItem = itemView.findViewById(R.id.textViewTempItem);
            textViewDescItem = itemView.findViewById(R.id.textViewDescItem);
            icWeather = itemView.findViewById(R.id.icWeather);
        }
    }
}
