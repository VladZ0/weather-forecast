package com.vlad.weatherforecast;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vlad.weatherforecast.forecast.City;
import com.vlad.weatherforecast.forecast.CurrentWeather;
import com.vlad.weatherforecast.forecast.DatedWeather;
import com.vlad.weatherforecast.forecast.Forecast;
import com.vlad.weatherforecast.services.ForecastService;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends ViewModel {
    private final MutableLiveData<Forecast> forecastMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> titleMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Forecast> getForecastMutableLiveData() {
        return forecastMutableLiveData;
    }

    public MutableLiveData<String> getTitleMutableLiveData() {
        return titleMutableLiveData;
    }

    private Forecast getForecastForTimezone(Forecast forecast, int filter){
        List<DatedWeather> newDatedWeatherList = new ArrayList<>();

        switch (filter){
            case Utils.FORECAST_CITY_TIME:
                for(DatedWeather datedWeather : forecast.getWeatherList()){
                    newDatedWeatherList.add(getNewWeather(datedWeather, forecast.getCity().getTimezone()*1000));
                }

                forecast.setWeatherList(newDatedWeatherList);
                return forecast;

            case Utils.CURRENT_TIME:
                for(DatedWeather datedWeather : forecast.getWeatherList()){
                    TimeZone timeZone = TimeZone.getDefault();
                    newDatedWeatherList.add(getNewWeather(datedWeather, timeZone.getOffset(new Date().getTime())));
                }

                forecast.setWeatherList(newDatedWeatherList);
                return forecast;

            case Utils.UTC_TIME:
                for(DatedWeather datedWeather : forecast.getWeatherList()){
                    newDatedWeatherList.add(getNewWeather(datedWeather, 0));
                }

                forecast.setWeatherList(newDatedWeatherList);
                return forecast;

            default:
                return null;
        }
    }

    private DatedWeather getNewWeather(DatedWeather datedWeather, long offset){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        datedWeather.setDtTxt(sdf
                .format(new Date(datedWeather.getDt() * 1000 + offset)));
        datedWeather.setDt(datedWeather.getDt() * 1000 + offset);

        return datedWeather;
    }

    //init forecast data according to time filter
    public void initForecastData(String cityName, int forecastTimeFilter, int forecastDateLocaleFilter){
        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(Utils.WEATHER_API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

        ForecastService service = retrofit.create(ForecastService.class);

        if(forecastTimeFilter == Utils.FORECAST_NOW){
            initNowForecast(service, cityName, forecastDateLocaleFilter);
        }

        if(forecastTimeFilter == Utils.FORECAST_PER_THREE_HOURS){
            initEveryThreeHoursForecast(service, cityName, forecastDateLocaleFilter);
        }
    }

    private void  initEveryThreeHoursForecast(ForecastService service, String cityName, int forecastDateLocaleFilter){
        Call<Forecast> forecastCall = service.getForecast(cityName, Utils.API_KEY);

        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(@NotNull Call<Forecast> call, @NotNull Response<Forecast> response) {
                if(response.isSuccessful()){
                    titleMutableLiveData.setValue(cityName.substring(0, 1).toUpperCase() + cityName.substring(1));
                    forecastMutableLiveData.setValue(getForecastForTimezone(response.body(), forecastDateLocaleFilter));
                }
                else{
                    forecastMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Forecast> call, @NotNull Throwable t) {
                forecastMutableLiveData.setValue(null);
            }
        });
    }

    private void initNowForecast(ForecastService service, String cityName, int forecastDateLocaleFilter){
        Call<CurrentWeather> currentWeatherCall = service
                .getCurrentWeather(cityName, Utils.API_KEY);

        currentWeatherCall.enqueue(new Callback<CurrentWeather>() {
            @Override
            public void onResponse(@NotNull Call<CurrentWeather> call, @NotNull Response<CurrentWeather> response) {
                if(response.isSuccessful()){
                    List<DatedWeather> datedWeather = new ArrayList<>();
                    datedWeather.add(new DatedWeather(Objects.requireNonNull(response.body())));

                    // api information refresh just once in 10 minutes
                    // so we will get date later, to avoid it, i set current date
                    datedWeather.get(0).setDt(new Date().getTime()/1000);

                    Forecast nowForecast = new Forecast();
                    nowForecast.setWeatherList(datedWeather);
                    nowForecast.setCity(new City(response.body().getCityName(), response.body().getTimezone()));

                    titleMutableLiveData.setValue(cityName.substring(0, 1).toUpperCase() + cityName.substring(1));
                    forecastMutableLiveData.setValue(getForecastForTimezone(nowForecast, forecastDateLocaleFilter));
                }
                else{
                    forecastMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<CurrentWeather> call, @NotNull Throwable t) {
                forecastMutableLiveData.setValue(null);
            }
        });
    }
}
