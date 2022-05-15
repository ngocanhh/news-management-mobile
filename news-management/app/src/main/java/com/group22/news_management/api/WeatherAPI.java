package com.group22.news_management.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.group22.news_management.model.ResponseWeatherData;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    WeatherAPI weatherApi = new Retrofit.Builder()
                            .baseUrl("https://api.openweathermap.org/")
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
                            .create(WeatherAPI.class);

    @GET("data/2.5/forecast")
    Call<ResponseWeatherData> getWeatherData (@Query("q") String cityName,
                                              @Query("units") String units,
                                              @Query("cnt") int cnt,
                                              @Query("appid") String appid,
                                              @Query("lang") String lang);
}
