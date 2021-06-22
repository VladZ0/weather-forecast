package com.vlad.weatherforecast.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DatedWeather extends AbstractWeather {
    @SerializedName("dt_txt")
    private String dtTxt;

    public DatedWeather(AbstractWeather weather){
        this.setDt(weather.getDt());
        this.setMain(weather.getMain());
        this.setVisibility(weather.getVisibility());
        this.setWeather(weather.getWeather());
        this.setWind(weather.getWind());
        this.setDtTxt("");
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
