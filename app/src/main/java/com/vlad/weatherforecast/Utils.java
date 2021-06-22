package com.vlad.weatherforecast;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
    private Utils(){}

    public static final int FORECAST_PER_THREE_HOURS = 0;
    public static final int FORECAST_NOW = 1;

    public static final int FORECAST_CITY_TIME = 0;
    public static final int CURRENT_TIME = 1;
    public static final int UTC_TIME = 2;

    public static final String API_KEY = "11624a7fa17aa336f2d1759938686410";
    public static final String WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    public static final String FORECAST = "forecast";
    public static final String WEATHER = "weather";
}
