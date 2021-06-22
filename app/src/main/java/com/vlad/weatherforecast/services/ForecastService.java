package com.vlad.weatherforecast.services;

import com.vlad.weatherforecast.Utils;
import com.vlad.weatherforecast.forecast.CurrentWeather;
import com.vlad.weatherforecast.forecast.Forecast;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ForecastService {
    @GET(Utils.WEATHER_API_BASE_URL + Utils.FORECAST)
    Call<Forecast> getForecast(@Query("q") String city, @Query("appid") String apiKey);

    @GET(Utils.WEATHER_API_BASE_URL + Utils.WEATHER)
    Call<CurrentWeather> getCurrentWeather(@Query("q") String city, @Query("appid") String apiKey);
}
