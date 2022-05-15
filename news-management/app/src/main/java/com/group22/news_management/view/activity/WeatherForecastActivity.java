package com.group22.news_management.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.group22.news_management.adapter.DailyWeatherForecastListAdapter;
import com.group22.news_management.api.WeatherAPI;
import com.group22.news_management.model.ResponseWeatherData;
import com.group22.news_management.model.WeatherForecastData;
import com.group22.newsmanagerment.R;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

public class WeatherForecastActivity extends AppCompatActivity {

    private static final String API_KEY = "4643fba20ad10f484128e1b325a60387";
    private ImageView imgWeatherCondition;
    private TextView textViewTemp, textViewCityName, textViewCloud, textViewHumidity,
            textViewWindSpeed, textViewCurrentDateTime, textViewDescription;
    private SearchView searchView;
    private List<WeatherForecastData> list;
    private RecyclerView recyclerView;
    private DailyWeatherForecastListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        initViews();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        getCurrentWeatherData("saigon");
        getWeatherDataNextSevenDays("saigon");
        searchViewEventOnSubmit();
    }

    private void searchViewEventOnSubmit() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String cityName = searchView.getQuery().toString();
                getCurrentWeatherData(cityName);
                getWeatherDataNextSevenDays(cityName);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initViews() {
        imgWeatherCondition = findViewById(R.id.imgWeatherCondition);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewCityName = findViewById(R.id.textViewCityName);
        textViewCloud = findViewById(R.id.textViewCloud);
        textViewHumidity = findViewById(R.id.textViewHumidity);
        textViewWindSpeed = findViewById(R.id.textViewWind);
        textViewCurrentDateTime = findViewById(R.id.textViewCurrentDateTime);
        textViewDescription = findViewById(R.id.textViewDescription);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recycleView);
    }

    // link: https://api.openweathermap.org/data/2.5/forecast?q=hanoi&units=metric&cnt=7&appid=4643fba20ad10f484128e1b325a60387
    public void getWeatherDataNextSevenDays(String cityName){
        WeatherAPI.weatherApi.getWeatherData(cityName, "metric", 8, API_KEY, "vi")
                    .enqueue(new Callback<ResponseWeatherData>() {
                        @Override
                        public void onResponse(Call<ResponseWeatherData> call, retrofit2.Response<ResponseWeatherData> response) {
                            ResponseWeatherData responseWeatherAPI = response.body();
                            if(responseWeatherAPI != null){
                                list = responseWeatherAPI.getList();
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WeatherForecastActivity.this);
                                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                adapter = new DailyWeatherForecastListAdapter(WeatherForecastActivity.this, list);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseWeatherData> call, Throwable t) {
                            Toast.makeText(WeatherForecastActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    public void getCurrentWeatherData(String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API_KEY + "&units=metric&lang=vi";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray weatherJsonArray = response.getJSONArray("weather");
                        JSONObject weatherJsonObject = weatherJsonArray.getJSONObject(0);
                        String description = weatherJsonObject.getString("description");
                        String firstCharacter = description.substring(0, 1);
                        String remaining = description.substring(1);
                        description = firstCharacter.toUpperCase() + remaining;
                        textViewDescription.setText(description);
                        textViewCityName.setText(response.getString("name"));
                        String icon = weatherJsonObject.getString("icon");
                        String urlIcon = "https://openweathermap.org/img/wn/" + icon + ".png";
                        Picasso.get().load(urlIcon).into(imgWeatherCondition);
                        JSONObject mainJsonObject = response.getJSONObject("main");
                        String tempStr = mainJsonObject.getString("temp");
                        float temp = Float.parseFloat(tempStr);
                        textViewTemp.setText(Math.round(temp) + "°C");
                        String humidity = mainJsonObject.getString("humidity");
                        textViewHumidity.setText(humidity + "%");
                        JSONObject windJsonObject = response.getJSONObject("wind");
                        String wind = windJsonObject.getString("speed");
                        textViewWindSpeed.setText(wind + "m/s");
                        JSONObject cloudsJsonObject = response.getJSONObject("clouds");
                        String clouds = cloudsJsonObject.getString("all");
                        textViewCloud.setText(clouds + "%");
                        String dt = response.getString("dt");
                        Date currentDate = new Date(Long.parseLong(dt) * 1000);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd-MM-yyyy");
                        textViewCurrentDateTime.setText(simpleDateFormat.format(currentDate));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(WeatherForecastActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}