package com.vlad.weatherforecast.forecast;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

public class MainWeatherInfo {
    private String main;
    private String description;

    @SerializedName("icon")
    private String iconName;


    public String getMain() {
        return main;
    }

    public String getDescription() {
        return description;
    }

    public String getIconName() {
        return iconName;
    }
}
