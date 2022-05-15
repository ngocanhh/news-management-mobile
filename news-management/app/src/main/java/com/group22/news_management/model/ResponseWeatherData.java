package com.group22.news_management.model;

import java.util.List;

public class ResponseWeatherData {

    private String cod;
    private int message;
    private int cnt;
    private List<WeatherForecastData> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<WeatherForecastData> getList() {
        return list;
    }

    public void setList(List<WeatherForecastData> list) {
        this.list = list;
    }
}
