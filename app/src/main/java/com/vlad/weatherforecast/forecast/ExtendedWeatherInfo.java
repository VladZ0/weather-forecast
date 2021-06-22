package com.vlad.weatherforecast.forecast;

import com.google.gson.annotations.SerializedName;

public class ExtendedWeatherInfo {
    private double temp;
    private int pressure;
    private int humidity;

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }
}
