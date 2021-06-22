package com.vlad.weatherforecast.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather extends AbstractWeather {
    private long timezone;

    @SerializedName("name")
    private String cityName;

    public long getTimezone() {
        return timezone;
    }

    public String getCityName() {
        return cityName;
    }
}
