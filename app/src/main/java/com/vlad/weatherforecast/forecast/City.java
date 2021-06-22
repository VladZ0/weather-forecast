package com.vlad.weatherforecast.forecast;

public class City {
    private String name;
    private String country;
    private long timezone;

    public City(String name, long timezone){
        this.name = name;
        this.timezone = timezone;
        this.country = "";
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getTimezone() {
        return timezone;
    }
}
