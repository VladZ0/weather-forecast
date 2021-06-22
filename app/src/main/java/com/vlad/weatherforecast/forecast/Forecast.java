package com.vlad.weatherforecast.forecast;

import java.util.List;

public class Forecast {
    private List<DatedWeather> list;
    private City city;

    public List<DatedWeather> getWeatherList() {
        return list;
    }

    public void setWeatherList(List<DatedWeather> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
