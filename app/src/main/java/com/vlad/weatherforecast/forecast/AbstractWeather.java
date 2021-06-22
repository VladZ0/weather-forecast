package com.vlad.weatherforecast.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class AbstractWeather {
    private long dt;
    private ExtendedWeatherInfo main;
    private List<MainWeatherInfo> weather;
    private Wind wind;
    private int visibility;

    public ExtendedWeatherInfo getMain() {
        return main;
    }

    public void setMain(ExtendedWeatherInfo main) {
        this.main = main;
    }

    public List<MainWeatherInfo> getWeather() {
        return weather;
    }

    public void setWeather(List<MainWeatherInfo> weather) {
        this.weather = weather;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }
}
